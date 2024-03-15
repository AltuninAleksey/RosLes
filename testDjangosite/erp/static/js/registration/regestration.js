setDataInPage();

async function setDataInPage() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/subjectRF",
        responseType: 'json'
    });

    var allSubject = requestData.data.get.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; })

    let subjectNode = document.querySelector("#subjectStatement");
    let newHtml = "";

    for(var i = 0; i < allSubject.length; i++) {
        if(i == 0) {
            newHtml = newHtml + "<option selected value=\"" + allSubject[i].id + "\">" + allSubject[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + allSubject[i].id + "\">" + allSubject[i].name_subject_RF + "</option>";
        }
    }

    subjectNode.innerHTML = newHtml;
}



document.getElementById('phone').addEventListener('keyup', function(event){
    let num=this.value;
    if (num.length==1 || num.length==5 || num.length==9)
     this.value=this.value+" ";
 });

function show_hide_password(target){
	var input = document.getElementById('password-input');
	if (input.getAttribute('type') == 'password') {
		target.classList.add('view');
		input.setAttribute('type', 'text');
	} else {
		target.classList.remove('view');
		input.setAttribute('type', 'password');
	}
	return false;
}


async function getStartRegistration() {

    let fio = document.querySelector("#fio");
    let email = document.querySelector("#email");
    let password = document.querySelector("#password-input");
    let phone = document.querySelector("#phone");
    let subject_rf = document.querySelector("#subjectStatement");

    let data = {
        FIO: fio.value,
        email: email.value,
        password: password.value,
        phoneNumber: phone.value,
        subject_rf: Number(subject_rf.value)
    }

    var code = await sartRegistration(data);

    if(code == "201") {
        getAvtorization();
    } else {
        alert("Ошибка регистрации!");
    }
}

async function sartRegistration(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/registration",
        data: data,
        responseType: 'json'
    });

    return requestData.data.code;
}
