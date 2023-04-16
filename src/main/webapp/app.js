function showAlert(){
    alert("Alert from js file");
}

var request = new XMLHttpRequest();
request.onreadystatechange = function() {
    if(request.readyState === 4){
        if(request.status === 200) {
            window.location.href = ""
        }
    }
}
