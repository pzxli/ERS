window.onload = async function(){

    // Clear form fields on load
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.reset();
    }

    // Check if user is already logged in
    let response = await fetch(`${domain}/session`, {
        credentials: "include"
    });
    let responseBody = await response.json();

    if(responseBody.success){
        window.location = "./employeedashboard";
    }
}

// Clear form if loaded from back-forward cache
window.addEventListener('pageshow', (event) => {
    if (event.persisted) {
      const loginForm = document.getElementById("login-form");
      if (loginForm) {
        loginForm.reset();
      }
    }
  });


// Login Form submission
document.getElementById("login-form").addEventListener("submit", async function (event){

    event.preventDefault();
    
    let usernameInputElem = document.getElementById("username");
    let passwordInputElem = document.getElementById("password");

    let user = {
        username: usernameInputElem.value,
        password: passwordInputElem.value
    }

    let response = await fetch(`${domain}/session`, {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include"
    });

    let responseBody = await response.json();

    if(responseBody.success == false){
        let messageElem = document.getElementById("message")
        messageElem.innerText = responseBody.message
    }else{
        // store user's information in localStorage
        localStorage.setItem("firstname", responseBody.data.firstname);
        localStorage.setItem("userId", responseBody.data.id);
        localStorage.setItem("role", responseBody.data.role);

    }

    // redirect to employee dashboard or manager dashboard
    if (responseBody.data.role.toLowerCase() === "employee") {
        window.location = `./employeedashboard?userId=${responseBody.data.id}`;
    } else {
        window.location = `./managerdashboard?userId=${responseBody.data.id}`;
    }


})