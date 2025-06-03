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

    user = responseBody.data;
    localStorage.setItem("firstname", user.firstname);
    localStorage.setItem("role", user.role);
    localStorage.setItem("userId", user.id);

    // Set title of page to "ERS - Request for *User's first name*"
    document.title = `ERS - Request for ${user.firstname}`;

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

const reimbursementForm = document.getElementById('Reimbursement-form');

if (reimbursementForm) {
  reimbursementForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(reimbursementForm);
    const typeString = formData.get('reimbursementType');

    const reimbursementTypeMap = {
      LODGING: 1,
      FOOD: 2,
      TRAVEL: 3
    };

    const payload = {
      amount: parseFloat(formData.get('amount')),
      description: formData.get('description').trim(),
      author: user?.id,
      typeId: reimbursementTypeMap[typeString]  // <-- integer ID here
    };

    if (!payload.author) {
      alert('User not identified. Please log in again.');
      return;
    }

    try {
      const res = await fetch(`${domain}/user/${payload.author}/list`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(payload),
      });

      const result = await res.json();

      if (res.ok && result.success) {
        alert('Reimbursement submitted successfully!');
        reimbursementForm.reset();
        window.location.href = '../pastreimbursements';
      } else {
        alert('Submission failed: ' + (result.message || 'Unknown error'));
      }
    } catch (err) {
      console.error('Submission error:', err);
      alert('Error submitting reimbursement. Please try again.');
    }
  });
}

