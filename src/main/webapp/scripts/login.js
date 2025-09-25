var username = document.getElementById("username");
var password = document.getElementById("password");
var message = document.getElementById("validation-message");
var btnReg = document.querySelector(".btnSubmit");

btnReg.addEventListener("click",(e)=>{
    e.preventDefault();
    console.log("test");
    console.log(username.value + password.value);
    loginForm(username.value,password.value);
})

function loginForm(username,password){
    fetch('/login',{
        method:'POST',
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            username:username,
            password:password
        })
    })
    .then(response => response.text())
    .then(data => {
        console.log('Server Response:', data);
        responseValidation(data);
    })
}

function responseValidation(data) {
    try {
        const response = JSON.parse(data);
        message.textContent = response.message;
        message.className = '';
        if(response.status === 'success-login'){
            message.classList.add("text-success");
        }else{
            message.classList.add("text-danger");
        }
    } catch (e) {
        console.error('Invalid JSON from server:', data);
        message.textContent = "Unexpected server response.";
        message.className = "text-warning";
    }
}

