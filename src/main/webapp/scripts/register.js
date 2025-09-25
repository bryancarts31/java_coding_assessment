var username = document.getElementById("username");
var password = document.getElementById("password");
var con_password = document.getElementById("con-password");
var email = document.getElementById("email");
var message = document.getElementById("validation-message");

var btnReg = document.querySelector(".btnSubmit");

btnReg.addEventListener("click", (e) => {
    e.preventDefault();
    registerForm(username.value, password.value, con_password.value, email.value);
});

function registerForm(username, password, con_password, email) {
    fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            username: username,
            password: password,
            confirmPassword: con_password,
            email: email
        })
    })
    .then(response => response.text())
    .then(data => {
        console.log('Server response:', data);
        responseValidation(data);
    });
}

function responseValidation(data) {
    try {
        const response = JSON.parse(data);
        message.textContent = response.message;

        if(response.status === 'success-register' && response.redirectUrl){
        setTimeout(()=>{
            console.log(response.redirectUrl);
            window.location.href = response.redirectUrl;
        },500);
       }
    } catch (e) {
        console.error('Invalid JSON from server:', data);
        message.textContent = "Unexpected server response.";
    }
}