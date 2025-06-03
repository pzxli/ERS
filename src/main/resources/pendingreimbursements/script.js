let user;

window.onload = async function () {
  preventBackCache();

  const sessionResponse = await fetch(`${domain}/session`, {
    credentials: "include",
  });
  const sessionData = await sessionResponse.json();

  if (!sessionData.success || sessionData.data.role !== "Manager") {
    alert("Unauthorized access.");
    window.location.href = "../";
    return;
  }

  user = sessionData.data;

  // Save user info to localStorage
  localStorage.setItem("firstname", user.firstname);
  localStorage.setItem("role", user.role);
  localStorage.setItem("userId", user.id);

  // Change title to "ERS - *User's role* view"
  document.title = `ERS - ${user.role} view`;

  // Logout Button
  const logoutBtn = document.getElementById("logout-btn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", handleLogout);
  }

  // Redirect to allreimbursements
  const pendingBtn = document.getElementById("pending-btn");
  if (pendingBtn) {
    pendingBtn.addEventListener("click", () => {
      window.location.href = `../allreimbursements`;
    });
  }

  await loadFilteredReimbursements(1);

  // Filter Reimbursements
  const statusFilter = document.getElementById("status-filter");
  if (statusFilter) {
    statusFilter.addEventListener("change", async () => {
        const selected = statusFilter.value;

        if (selected === "") {
        await loadFilteredReimbursements(1, false); // Old to new
        } else if (selected === "newest") {
        await loadFilteredReimbursements(1, true); // New to old
        }
    });
  }


};

// Load Reimbursements - Default old to new
async function loadAllReimbursements(authorId, reverse = false) {
  try {
    const response = await fetch(`${domain}/user/${authorId}/all`, {
      credentials: "include",
    });

    const result = await response.json();

    if (response.ok && result.success && Array.isArray(result.data)) {
      const sorted = result.data.sort((a, b) => {
        const dateA = new Date(a.submitted);
        const dateB = new Date(b.submitted);
        return reverse ? dateB - dateA : dateA - dateB;
      });

      renderTable(sorted);
    } else {
      alert("Failed to fetch reimbursements.");
    }
  } catch (err) {
    console.error("Error loading reimbursements:", err);
    alert("Error loading reimbursements.");
  }
}

// Load Filtered Reimbursements
async function loadFilteredReimbursements(statusId, reverse = false) {
  try {
    const response = await fetch(`${domain}/user/${user.id}/all`, {
      credentials: "include",
    });

    const result = await response.json();

    if (response.ok && result.success && Array.isArray(result.data)) {
      const filtered = result.data.filter(r => {
        if (statusId === 1) return r.status.toLowerCase() === "pending";
        if (statusId === 2) return r.status.toLowerCase() === "approved";
        if (statusId === 3) return r.status.toLowerCase() === "denied";
        return true;
      });

      const sorted = filtered.sort((a, b) => {
        const dateA = new Date(a.submitted);
        const dateB = new Date(b.submitted);
        return reverse ? dateB - dateA : dateA - dateB;
      });

      renderTable(sorted);
    } else {
      alert("Failed to filter reimbursements.");
    }
  } catch (err) {
    console.error("Error filtering reimbursements:", err);
    alert("Error filtering reimbursements.");
  }
}

function renderTable(reimbursements) {
  const tbody = document.querySelector("#reimbursement-table tbody");
  tbody.innerHTML = "";

  reimbursements.forEach((r) => {
    const tr = document.createElement("tr");

    const isPending = r.status.toLowerCase() === "pending";
    const actionButtons = isPending
      ? `
        <button class="btn btn-success btn-sm me-1" onclick="handleApprove(${r.id})">Approve</button>
        <button class="btn btn-danger btn-sm" onclick="handleDeny(${r.id})">Deny</button>
      `
      : "—";

    tr.innerHTML = `
      <td>${r.id}</td>
      <td>${r.authorFullName}</td>
      <td>${r.type}</td>
      <td>$${r.amount.toFixed(2)}</td>
      <td>${r.description}</td>
      <td>${formatTimestamp(r.submitted)}</td>
      <td>${r.status}</td>
      <td>${r.resolved ? formatTimestamp(r.resolved) : "—"}</td>
      <td>${r.resolverFullName || "—"}</td>
      <td>${actionButtons}</td>
    `;

    tbody.appendChild(tr);
  });
}

// Approve Function
async function handleApprove(reimbId) {
  const confirmed = confirm("Approve this reimbursement?");
  if (!confirmed) return;

  try {
    const response = await fetch(`${domain}/user/${user.id}/list`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: reimbId,
        statusId: 2,
        resolverId: user.id,
      }),
    });

    if (response.ok) {
      alert("Reimbursement approved!");
      await loadFilteredReimbursements(1); // Reload pending list
    } else {
      alert("Failed to approve reimbursement.");
    }
  } catch (err) {
    console.error("Error approving reimbursement:", err);
    alert("Error approving reimbursement.");
  }
}

// Deny Function
async function handleDeny(reimbId) {
  const confirmed = confirm("Deny this reimbursement?");
  if (!confirmed) return;

  try {
    const response = await fetch(`${domain}/user/${user.id}/list`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: reimbId,
        statusId: 3, 
        resolverId: user.id,
      }),
    });

    if (response.ok) {
      alert("Reimbursement denied.");
      await loadFilteredReimbursements(1);
    } else {
      alert("Failed to deny reimbursement.");
    }
  } catch (err) {
    console.error("Error denying reimbursement:", err);
    alert("Error denying reimbursement.");
  }
}



function formatTimestamp(ts) {
  const d = new Date(ts);
  return d.toLocaleString();
}

// Logout function - Delete Session
async function handleLogout() {
  try {
    await fetch(`${domain}/session`, {
      method: "DELETE",
      credentials: "include",
    });
    localStorage.clear();
    window.location.href = "../";
  } catch (err) {
    alert("Logout failed.");
    console.error(err);
  }
}

// Prevent back-forward cache showing stale pages
function preventBackCache() {
  window.addEventListener("pageshow", function (event) {
    if (
      event.persisted ||
      window.performance.getEntriesByType("navigation")[0]?.type === "back_forward"
    ) {
      window.location.reload();
    }
  });
}
