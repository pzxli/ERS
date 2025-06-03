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
    document.title = `ERS - ${user.role}`;

    // Change Text on top to "Welcome - *User's first name*"
    document.getElementById("welcome").innerHTML = `<h2>Welcome, ${user.firstname}</h2>`;

    // Logout button
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

// Redirect to pastreimbursements
const viewPastReimbursement = document.getElementById("ViewPast-form");
    if (viewPastReimbursement) {
        viewPastReimbursement.addEventListener("submit", viewReimbursement);
    }

function viewReimbursement(event) {
    event.preventDefault();
    window.location = `../pastreimbursements?userId=${user.id}`;
}

// Redirect to submitreimbursement
const submitReimbursement = document.getElementById("Reimbursement-form");
    if (submitReimbursement) {
        submitReimbursement.addEventListener("submit", createReimbursement);
    }

function createReimbursement(event) {
    event.preventDefault();
    window.location = `../submitreimbursement?userId=${user.id}`;
}