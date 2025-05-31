window.onload = async function(){

    // Clear form fields on load
    const loginForm = document.getElementById("login-form");
    if (loginForm) {
        loginForm.reset();
    }

    // Check if user is already logged in
    let response = await fetch(`${domain}/session`);
    let responseBody = await response.json();

    if(responseBody.success){
        window.location = "./dashboard";
    }
}

// Also clear form if loaded from back-forward cache
window.addEventListener('pageshow', (event) => {
    if (event.persisted) {
      const loginForm = document.getElementById("login-form");
      if (loginForm) {
        loginForm.reset();
      }
    }
  });


/* function that runs when the page loads */
document.getElementById("login-form").addEventListener("submit", async function (event){
    //this is to stop the form from reloading 
    event.preventDefault();
    
    //retrieve input elements from the dom
    let usernameInputElem = document.getElementById("username");
    let passwordInputElem = document.getElementById("password");

    //get values from the input elements and put it into an object
    let user = {
        username: usernameInputElem.value,
        password: passwordInputElem.value
    }

    //send the http request
    let response = await fetch(`${domain}/session`, {
        method: "POST",
        body: JSON.stringify(user)
    })

    //retrieve the response body
    let responseBody = await response.json();


    //logic after response body
    if(responseBody.success == false){
        let messageElem = document.getElementById("message")
        messageElem.innerText = responseBody.message
    }else{
        // store user's information in localStorage
        localStorage.setItem("firstname", responseBody.data.firstname);
        localStorage.setItem("userId", responseBody.data.id);

        window.location = `./dashboard?userId=${responseBody.data.id}`;

    }


})