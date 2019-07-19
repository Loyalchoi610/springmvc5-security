window.addEventListener('load', function (ev) {
    let info={
        dom_id: document.getElementById('username'),
        dom_pw: document.getElementById('password'),
        dom_message: document.getElementById('message'),
    };
    loginResult = function(e){
        info.dom_message.innerText = this.responseText;
    };
    document.getElementById('doLogin').addEventListener('click',function () {
      let xhr = new XMLHttpRequest();
      xhr.open('POST','/login-processing');
      xhr.setRequestHeader('X-CSRF-TOKEN',csrf.value);
      xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
      xhr.addEventListener('load',loginResult);
      xhr.send('username='+info.dom_id.value+'&password='+info.dom_pw.value);
    })
});