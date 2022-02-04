const firebaseConfig = {
    apiKey: "AIzaSyDPTDzASeNW7VQc4alYJUxsn1vqPfTKOzc",
    authDomain: "duchan-adfbe.firebaseapp.com",
    projectId: "duchan-adfbe",
    storageBucket: "duchan-adfbe.appspot.com",
    messagingSenderId: "183989799361",
    appId: "1:183989799361:web:5607dd26342dfe85d1c740",
    measurementId: "G-Q651B759MG"
};

firebase.initializeApp(firebaseConfig);

const provider = new firebase.auth.GoogleAuthProvider();

const signInForm = document.getElementById("signInForm");

const txtEmail = document.getElementById("txtEmail");
const txtPassword = document.getElementById("txtPassword");
const txtToken = document.getElementById("txtToken");
const txtUserId = document.getElementById("txtUserId");

const errorAlert = document.getElementById("errorAlert");
const errorMessage = document.getElementById("errorMessage");
const alertCloseButton = document.getElementById("alertCloseButton");

const btnSignIn = document.getElementById("btnSignIn");
const btnSignInSpinner = document.getElementById("btnSignInSpinner");

const btnGoogleSignIn = document.getElementById("btnGoogleSignIn");
const btnGoogleSignInSpinner = document.getElementById("btnGoogleSignInSpinner");

class ResponseType {
    static success = "success";
    static failure = "failure";
}

class Response {
    #responseType;
    #message;

    constructor(responseType, message) {
        this.#responseType = responseType;
        this.#message = message;
    }

    get getResponseType() {
        return this.#responseType;
    }

    get getMessage() {
        return this.#message;
    }
}

window.addEventListener("load",  e => {
    initSignInListeners();
})

function initSignInListeners() {
    signInForm.addEventListener("submit", e => handleFormSubmit(e));

    btnGoogleSignIn.addEventListener("click", e => handleGoogleSignIn(e));

    alertCloseButton.addEventListener("click", event => {
        event.preventDefault();
        errorAlert.classList.add("d-none");
    })
}

async function handleGoogleSignIn(event) {
    event.preventDefault();

    const signInResponse = await signInUserWithGoogle();

    if (signInResponse.getResponseType === ResponseType.success) {
        signInForm.submit();
    } else if (signInResponse.getResponseType === ResponseType.failure) {
        displayErrorMessage(signInResponse.getMessage);
    }
}

async function handleFormSubmit(event) {
    event.preventDefault();
    showLoadingSpinner();

    const signInResponse = await signInUser(txtEmail.value.trim(), txtPassword.value.trim());

    if (signInResponse.getResponseType === ResponseType.success) {
        hideLoadingSpinner();
        signInForm.submit();
    } else if(signInResponse.getResponseType === ResponseType.failure) {
        hideLoadingSpinner();
        displayErrorMessage(signInResponse.getMessage);
        focusPasswordField();
    }
}

function signInUser(email, password) {
    return new Promise(resolve => {
        firebase.auth().signInWithEmailAndPassword(email, password)
            .then((userCredential) => {
                txtToken.value = userCredential.user.Aa;
                txtUserId.value = userCredential.user.uid;
                resolve(
                    new Response(ResponseType.success, "Success!")
                );
            })
            .catch((err) => {
                hideLoadingSpinner();
                resolve(
                    new Response(ResponseType.failure, "Invalid username or password.")
                );
            });
    });
}

function signInUserWithGoogle() {
    return new Promise(resolve => {
        firebase.auth().signInWithPopup(provider)
            .then((result) => {
                txtUserId.value = result.user.uid;
                txtToken.value = result.user.Aa;
                resolve(
                    new Response(ResponseType.success, "Success!")
                );
            })
            .catch((error) => {
                resolve(
                    new Response(ResponseType.failure, "Sign in failed. Please try again.")
                );
            });
    });
}

function displayErrorMessage(message) {
    errorAlert.classList.remove("d-none");
    errorMessage.innerHTML = message;
    txtPassword.value = "";
}

function showLoadingSpinner() {
    btnSignIn.disabled = true;
    btnGoogleSignIn.disabled = true;
    btnSignInSpinner.classList.remove("d-none");
}

function hideLoadingSpinner() {
    btnSignIn.disabled = false;
    btnGoogleSignIn.disabled = false;
    btnSignInSpinner.classList.add("d-none");
}

function focusPasswordField() {
    txtPassword.focus();
}