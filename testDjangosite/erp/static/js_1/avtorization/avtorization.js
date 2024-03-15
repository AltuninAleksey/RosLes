async function getAvtorization() {

    let email = document.querySelector("#email");
    let password = document.querySelector("#password");

    let data = {
        email: email.value,
        password: password.value
    }

    await sartAvtorization(data);
}

async function sartAvtorization(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/v2/login",
        data: data,
        responseType: 'json'
    });

    console.log(requestData);
}