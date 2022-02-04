const registerForm = document.getElementById("registerForm");

const txtFirstName = document.getElementById("txtFirstName");
const txtLastName = document.getElementById("txtLastName");

const txtEmailRegister = document.getElementById("txtEmailRegister");
const txtPasswordRegister = document.getElementById("txtPasswordRegister");
const txtPasswordConfirm = document.getElementById("txtPasswordConfirmRegister");

const txtTokenRegister = document.getElementById("txtTokenRegister");
const txtUserIdRegister = document.getElementById("txtUserIdRegister");

const errorAlertRegister = document.getElementById("errorAlertRegister");
const errorMessageRegister = document.getElementById("errorMessageRegister");
const alertCloseButtonRegister = document.getElementById("alertCloseButtonRegister");

const btnRegister = document.getElementById("btnRegister");
const btnRegisterSpinner = document.getElementById("btnRegisterSpinner");

window.addEventListener("load", e => {
    initListeners();
})

function initListeners() {
    registerForm.addEventListener("submit", e => registerFormHandler(e));

    alertCloseButtonRegister.addEventListener("click", event => {
        event.preventDefault();
        errorAlertRegister.classList.add("d-none");
    });

    txtPasswordConfirm.addEventListener("change", () => {
        txtPasswordConfirm.setCustomValidity("");
        if (txtPasswordRegister.value.trim() !== txtPasswordConfirm.value.trim()) {
            txtPasswordConfirm.setCustomValidity("Passwords must match!");
        }
    });
}

async function registerFormHandler(event) {
    event.preventDefault();
    showLoadingSpinnerRegister();

    const registerResponse = await registerUser(
        txtEmailRegister.value.trim(), txtPasswordRegister.value.trim()
    )
    if (registerResponse.getResponseType === ResponseType.success) {
        hideLoadingSpinnerRegister();
        registerForm.submit();
    } else if (registerResponse.getResponseType === ResponseType.failure) {
        hideLoadingSpinnerRegister();
        displayErrorMessageRegister(registerResponse.getMessage);
    }
}

function registerUser(email, password) {
    return new Promise(resolve => {
        firebase.auth().createUserWithEmailAndPassword(email, password)
            .then((userCredential) => {
                txtTokenRegister.value = userCredential.user.Aa;
                txtUserIdRegister.value = userCredential.user.uid;
                resolve(
                    new Response(ResponseType.success, "Success!")
                );
            })
            .catch((error) => {
                hideLoadingSpinnerRegister();
                resolve(
                    new Response(ResponseType.failure, "User already exists.")
                );
            });
    });
}

function displayErrorMessageRegister(message) {
    errorAlertRegister.classList.remove("d-none");
    errorMessageRegister.innerHTML = message;
    txtPasswordRegister.value = "";
    txtPasswordConfirm.value = "";
}

function showLoadingSpinnerRegister() {
    btnRegister.disabled = true;
    btnRegisterSpinner.classList.remove("d-none");
}

function hideLoadingSpinnerRegister() {
    btnRegister.disabled = false;
    btnRegisterSpinner.classList.add("d-none");
}
