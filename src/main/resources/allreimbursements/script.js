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

  // Go to Pending Reimbursements
  const pendingBtn = document.getElementById("pending-btn");
  if (pendingBtn) {
    pendingBtn.addEventListener("click", () => {
      window.location.href = `../pendingreimbursements`;
    });
  }

  await loadAllReimbursements(user.id);

  // Filter Reimbursements
  const statusFilter = document.getElementById("status-filter");
  if (statusFilter) {
    statusFilter.addEventListener("change", async () => {
        const selected = statusFilter.value;

        if (selected === "") {
        await loadAllReimbursements(user.id); // Old to new
        } else if (selected === "newest") {
        await loadAllReimbursements(user.id, true); // New to old
        } else {
        await loadFilteredReimbursements(parseInt(selected));
        }
    });
  }


};

// Default Reimbursements Old to New
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

// Filtered Reimbursements
async function loadFilteredReimbursements(statusId) {
  try {
    const response = await fetch(`${domain}/user/${user.id}/all`, {
      credentials: "include",
    });

    const result = await response.json();

    if (response.ok && result.success && Array.isArray(result.data)) {
      const filtered = result.data.filter(r => {
        // Assume r.status string equals "Pending", "Approved", or "Denied"
        if (statusId === 1) return r.status.toLowerCase() === "pending";
        if (statusId === 2) return r.status.toLowerCase() === "approved";
        if (statusId === 3) return r.status.toLowerCase() === "denied";
        return true;
      });
      renderTable(filtered);
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
    `;

    tbody.appendChild(tr);
  });
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
        if (event.persisted || window.performance.getEntriesByType("navigation")[0]?.type === "back_forward") {
            window.location.reload();
        }
    });
}