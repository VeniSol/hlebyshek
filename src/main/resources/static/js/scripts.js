window.onscroll = function () {
    scrollFunction();
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.querySelector(".stt").style.display = "block";
    } else {
        document.querySelector(".stt").style.display = "none";
    }
}
const formOpenBtn = document.querySelector("#form-open"),
    home = document.querySelector(".home"),
    formContainer = document.querySelector(".form_container"),
    formCloseBtn = document.querySelector(".form_close"),
    signupBtn = document.querySelector("#signup"),
    loginBtn = document.querySelector("#login");

formOpenBtn.addEventListener("click",() => home.classList.add("show"));

formCloseBtn.addEventListener("click",() => home.classList.remove("show"));
signupBtn.addEventListener("click",(e) =>{
    e.preventDefault();
    formContainer.classList.add("active");
});
loginBtn.addEventListener("click",(e) =>{
    e.preventDefault();
    formContainer.classList.remove("active");
});
const pwShowHide = document.querySelectorAll(".pw_hide");

pwShowHide.forEach((icon) => {
    icon.addEventListener("click",() => {
        let getPwInput = icon.parentElement.querySelector("input");
        if(getPwInput.type === "password"){
            getPwInput.type="text";
            icon.classList.replace("uil-eye-slash","uil-eye");
        }else{
            getPwInput.type="password";
            icon.classList.replace("uil-eye","uil-eye-slash");
        }
    });
});

