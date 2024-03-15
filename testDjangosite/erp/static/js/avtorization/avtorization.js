async function getAvtorization() {

    let email = document.querySelector("#email");
    let password = document.querySelector("#password");

    let data = {
        email: email.value,
        password: password.value
    }

    var token = await sartAvtorization(data);

    document.cookie = "jwttoken=" + token;

    getStatementRecalculations();
}

async function sartAvtorization(data) {

    try {
        var requestData = await axios({
            method: 'post',
            url: urlGlobal + "/v2/login",
            data: data,
            responseType: 'json'
        });
    }
    catch(e) {
        alert("Введен неверный логин или пароль!");
    }

    return requestData.data.access;
}