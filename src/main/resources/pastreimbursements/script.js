let user;

window.onload = async function () {
    preventBackCache();

    // Session check
    let response = await fetch(`${domain}/session`, {
        credentials: "include"  
    });
    let responseBody = await response.json();

    if (!responseBody.success) {
        window.location = "../"; // Redirect to login
        return;
    }

    // Set title of page to "ERS - *User's role*"
    user = responseBody.data;
    localStorage.setItem("firstname", user.firstname);
    localStorage.setItem("role", user.role);
    localStorage.setItem("userId", user.id);

    // Set title of page to "ERS - *User's first name*'s Reimbursements"
    document.title = `ERS - ${user.firstname}'s Reimbursements`;

    // Logout button
    const logoutBtn = document.getElementById("logout-btn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", handleLogout);
    }

    await loadReimbursements(user.id);
};

// Prevent back-forward cache showing stale pages
function preventBackCache() {
    window.addEventListener("pageshow", function (event) {
        if (event.persisted || window.performance.getEntriesByType("navigation")[0]?.type === "back_forward") {
            window.location.reload();
        }
    });
}

// Logout function - Delete Session
async function handleLogout() {
    try {
        await fetch(`${domain}/session`, {
            method: "DELETE",
            credentials: "include"
        });
        localStorage.clear();
        window.location.href = "../"; // Go back to login
    } catch (err) {
        alert("Logout failed.");
        console.error(err);
    }
}

// Load Reimbursements
async function loadReimbursements(userId) {
    const container = document.getElementById("reimbursements-list");
    container.innerHTML = "<p>Loading...</p>";

    try {
        const res = await fetch(`${domain}/user/${userId}/list`, {
            credentials: "include"
        });
        const body = await res.json();

        container.innerHTML = ""; // clear loading text

        if (!body.success || !body.data || body.data.length === 0) {
            container.innerHTML = "<p>No reimbursements found.</p>";
            return;
        }

        body.data.forEach((r) => {
            const entry = document.createElement("div");
            entry.className = "reimbursement-entry mb-3 pb-2 border-bottom";

            entry.innerHTML = `
                <strong>Amount:</strong> $${r.amount.toFixed(2)}<br>
                <strong>Description:</strong> ${r.description}<br>
                <strong>Status:</strong> ${r.status}<br>
                <strong>Type:</strong> ${r.type}<br>
                <strong>Submitted:</strong> ${new Date(r.submitted).toLocaleString()}<br>
                ${r.resolved ? `<strong>Resolved:</strong> ${new Date(r.resolved).toLocaleString()}<br>` : ""}
            `;

            container.appendChild(entry);
        });
    } catch (err) {
        console.error("Failed to load reimbursements:", err);
        container.innerHTML = "<p>Error loading reimbursements.</p>";
    }
}