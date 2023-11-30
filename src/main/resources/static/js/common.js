function findAddr(){
    new daum.Postcode({
        oncomplete:function(data){
            var roadAddr = data.roadAddress;
            var jibunAddr = data.jibunAddress;
            document.getElementById("postcode").value = data.zonecode;
            if(roadAddr !== ''){
                document.getElementById("address1").value = roadAddr;
            } else if(jibunAddr !== ''){
                document.getElementById("address1").value = jibunAddr;
            }
            document.getElementById("address2").focus();
        }
    }).open();
}