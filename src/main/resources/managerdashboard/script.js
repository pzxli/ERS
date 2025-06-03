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
    document.title = `ERS - ${user.role}`;
    document.getElementById("welcome").innerHTML = `<h2>Welcome, ${user.firstname}</h2>`;

    // Logout button setup
    const logoutBtn = document.getElementById("logout-btn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", handleLogout);
    }
};

// Prevent back-forward cache showing stale pages
function preventBackCache() {
    window.addEventListener("pageshow", function (event) {
        if (event.persisted || window.performance.getEntriesByType("navigation")[0]?.type === "back_forward") {
            window.location.reload();
        }
    });
}

// Delete session and log out
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

// Redirect to pastreimbursements
const viewAllReimbursement = document.getElementById("ViewAll-form");
    if (viewAllReimbursement) {
        viewAllReimbursement.addEventListener("submit", allReimbursement);
    }

function allReimbursement(event) {
    event.preventDefault();
    window.location = `../allreimbursements?userId=${user.id}`;
}