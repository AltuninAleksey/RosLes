const saveButtonProfile = document.querySelector('.save-profile');
saveButtonProfile.addEventListener('click', () => {
    doSomething('m1profile', document.querySelector('.modal-all-profile'));
});

let isFunc1 = true;
let doSomething = (elid, ell) => {
    if (isFunc1) {
        ShowModalProfile(elid);
    } else {
        HideModalProfile(ell);
    }
    isFunc1 = !isFunc1;
}
function ShowModalProfile(elid) {
    var modalAll = document.getElementById(elid);
    if (modalAll) {
        modalAll.style.display = "flex";
        document.body.style.overflow = 'hidden';
    }
}

function openChangePassword() {

    HideModalProfile(m1profile, event);

    document.getElementById('old_password').value = '';
    document.getElementById('new_password').value = '';
    document.getElementById('confirm_password').value = '';

    document.getElementById('m1changePassword').style.display = 'flex';
    document.body.style.overflow = 'hidden';
}
function saveNewPassword() {
    const oldPassword = document.getElementById('old_password').value;
    const newPassword = document.getElementById('new_password').value;
    const confirmPassword = document.getElementById('confirm_password').value;

    if (!oldPassword || !newPassword || !confirmPassword) {
        alert('Заполните все поля');
        return;
    }

    if (newPassword !== confirmPassword) {
        alert('Новый пароль и подтверждение не совпадают');
        return;
    }

    HideModalProfile(m1changePassword, event);
    alert('Пароль успешно изменен');
}

function HideModalProfile(ell, event){
    if (ell.classList.contains('modal-all-profile')) {
        ell.style.display = "none";
    }
    document.body.style.overflow = '';
    if (event) {
        event.preventDefault();
        isFunc1 = true;
    }
}

async function saveDataInProfile() {
    if(/\d/.test(document.querySelector("#profile_fio").value)) {
        alert("Имя не должно содержать цифры!");
        return;
    }


    data = {
        FIO: document.querySelector("#profile_fio").value,
        phoneNumber: document.querySelector("#profile_phone").value,
        id_subject_rf: document.querySelector("#subjectStatement-profile").value,
        id_user: Number(APP.userData.id_user)
    }

    await updateProfile(APP.userData.id, data);
    APP.userData = await CommonBusiness.getUserData();
    await setDataInProfile();

    getStatementRecalculations();
}

document.getElementById('profile_phone').addEventListener('keyup', function(event){
    let num=this.value;
    if (num.length==1 || num.length==5 || num.length==9)
     this.value=this.value+" ";
 });


async function setDataInProfile() {

    if(APP.allSubject == undefined || APP.allSubject == null) {
        APP.allSubject = await getAllSubjectrf();
    }

    document.querySelector("#profile_fio").value = APP.userData.FIO
    document.querySelector("#profile_phone").value = APP.userData.phoneNumber

    let newHtml = "";
    let profileSubject = document.querySelector("#subjectStatement-profile");

    for(var i = 0; i < APP.allSubject.length; i++) {
        if(APP.allSubject[i].id == Number(APP.userData.id_subject_rf)) {
            newHtml = newHtml + "<option selected value=\"" + APP.allSubject[i].id + "\">" + APP.allSubject[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allSubject[i].id + "\">" + APP.allSubject[i].name_subject_RF + "</option>";
        }
    }
    profileSubject.innerHTML = newHtml;
}

function ShowModal(elId, modalText, modalImage) {
  var modalAll = document.getElementById(elId);
  const modalContent = modalAll.querySelector('.modal');
  const modalTextElement = modalContent.querySelector('.modal-text');
  const modalImageElement = modalContent.querySelector('.modal-image');
  if (modalAll && modalTextElement && modalImageElement) {
    modalTextElement.textContent = modalText;
    modalImageElement.src = modalImage;
    modalAll.style.display = "flex";
    document.body.style.overflow = 'hidden';
  }
  setTimeout(function() {
    HideModal(modalAll);
  }, 2000);
}


function HideModal(ell) {
    if (ell.classList.contains('modal-all')) {
      ell.style.display = "none";
    }
    document.body.style.overflow = '';
}

async function downloadDocument() {
    const userManualUrl = urlGlobal + '/user_manual';
    const response = await fetch(userManualUrl);
    const data = await response.json();
    const filePath = data.path;
    const fullFileUrl = urlGlobal + filePath;
    const fileResponse = await fetch(fullFileUrl);
    const blob = await fileResponse.blob();
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'Руководство_Пользователя_ААС_ГМВЛ.docx';
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
}