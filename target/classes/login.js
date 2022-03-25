
// function login(){
//     let usernameInputElem = document.getElementById("username");
//     let passwordInputElem = document.getElementById("password");

//     console.log(usernameInputElem.value, passwordInputElem.value)
// }

async function login(event){
    event.preventDefault();

    let usernameInputElem = document.getElementById("username");
    let passwordInputElem = document.getElementById("password");

    console.log(usernameInputElem.ariaValueMax, passwordInputElem.value)

    let response = await fetch("http://localhost:9001/login"), {
        method: "POST",
        body: JSON.strinify({
            username: usernameInputElem.value,
            password: passwordInputElem.value
        }
    })

    let responseBody = await response.json();

    if(responseBody.success){
        if(responseBody.role == "MANAGER"){
            window.location = "./managerdashboard"
        }else{
            window.location = "./employeedashboard"
        }
    }


}


/* function that runs when the page loads */
/* document.getElementById("login-form").addEventListener("submit", async function (event){
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
    let response = await fetch("http://localhost:9001/login", {
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
        //console.log("Login Successful",responseBody.data)

        //redirect page to dashboard page if credentials were successful

        if(responseBody.data.id === 5){
            window.location = "./managerdashboard"
        }
        window.location = "./employeedashboard"
        //window.location = "./dashboard?userId=" + responseBody.data.id
        //window.location = `./dashboard?userId=${responseBody.data.id}`

    }

}) */


// recognizing user and pass and taking me to next url
// not recognizing user is employee or manager
// not taking me to the dashboard