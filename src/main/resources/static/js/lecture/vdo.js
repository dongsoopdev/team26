/* 버튼 선택자 */
var video = document.getElementById('video');
var restart = document.getElementById('restart');
var play = document.getElementById('play');
var pause = document.getElementById('pause');
var stop = document.getElementById('stop');
var timeCurrent = document.getElementById('timeCurrent');
var rew = document.getElementById('rew');
var fastFwd = document.getElementById('fastFwd');
var mute = document.getElementById('mute');
var unmute = document.getElementById('unmute');
var vol = document.getElementById('vol');
var vmup = document.getElementById('vmup');
var vmdown = document.getElementById('vmdown');
var full = document.getElementById('full');
var sizeup = document.getElementById('sizeup');
var sizedown = document.getElementById('sizedown');
var progress = document.getElementById('progress');
var progressBar = document.getElementById('progressBar');
var volTxt = document.getElementById('volTxt'); 
video.load();  //비디오 로딩
video.setAttribute("loop","5"); //비디오 반복 횟수 지정
video.volume = 1;   //비디오의 볼륨값 초기화

// video.play();

/* 동영상 재생 제어 처리부 */
//재시작
restart.addEventListener("click", function () {    restartPlay()   });
//10초 뒤로 가기
rew.addEventListener("click", function(){        skip(-10);    });
//10초 앞으로 가기  
fastFwd.addEventListener("click", function(){        skip(10);    });  
//재생 함수 호출
play.addEventListener("click", function () {     playPause ()       });
//일시정지 함수 호출
pause.addEventListener("click", function () {     playPause()      });
//정지 함수 호출
stop.addEventListener("click", function () {    stopPlayer ()    });
//음소거 함수 호출
mute.addEventListener("click", function () {    mutePlayer('mute')    });
//음삽입 함수 호출
unmute.addEventListener("click", function () {    mutePlayer('unmute')    });
//볼륨 조절바 기능 정의 및 호출
vol.addEventListener("change", function(){  
    video.volume = this.value/1000;
    volTxt.innerText = parseInt(video.volume*100)+"%";
});
vmup.addEventListener("click", function () {        volumeUpDown("up");    });//볼륨업
vmdown.addEventListener("click", function () {        volumeUpDown("down");    });//볼륨다운
full.addEventListener("click", function () {        fullScreen();    });//풀스크린
var w=640;
video.width=w;
sizeup.addEventListener("click", function(){    if(video.width < 640) video.width += 200;    }); //사이즈업
sizedown.addEventListener("click", function(){  if(video.width > 40) video.width -= 200;    }); //사이즈 다운

//처음부터 다시 재생
function restartPlay() {  video.play(); video.currentTime = 0;  }
//앞으로, 뒤로 가기 함수 
function skip(value) { video.currentTime += value } 
//재생일시정지 함수
function playPause () {  
    if (video.paused) { video.play(); play.style.display = "none"; pause.style.display = "inline-block";
    } else { video.pause(); play.style.display = "inline-block";   pause.style.display = "none";
    }
}   
//정지함수
function stopPlayer () { video.pause(); video.currentTime = 0; play.style.display = "inline-block";   pause.style.display = "none";}
/* 음량 제어 처리부 */
function mutePlayer (state) { //음소거토글함수
    if (state == "mute") { video.volume = 0;  mute.style.display = "none";
        unmute.style.display = "inline-block";  volTxt.innerText = "0%";
    } else if (state == "unmute") { video.volume = 1; mute.style.display = "inline-block";
        unmute.style.display = "none"; volTxt.innerText = "100%";
    }
}
function volumeUpDown (dir) { //볼륨updown
    if (dir == "up") {  
        if (video.volume == 1) {  return false;  }
        var change = video.volume + 0.1;    change = change.toFixed(1);
        video.volume = parseFloat(change); 
    } else if (dir =="down") {
        if (video.volume == 0) { mute.style.display = "none";
            unmute.style.display = "inline-block"; return false;
        }
        var change = video.volume - 0.1;   change = change.toFixed(1);
        video.volume = parseFloat(change);
    }
    volTxt.innerText = parseInt(video.volume*100)+"%";
}

//동영상 크기제어 처리부
function fullScreen () { //전체화면 만들기
    if (video.requestFullscreen) { video.requestFullscreen(); } 
    else if (video.mozRequestFullScreen) { video.mozRequestFullScreen(); } 
    else if (video.webkitRequestFullscreen) { video.webkitRequestFullscreen(); } 
    else if (video.msRequestFullscreen) { video.msRequestFullscreen(); }
}

//재생시간 표시 처리부
playTime();  //재생시간 표시 함수
progressPlayer();  //프로그레스바 늘리기 함수
var current, currentmin, currentsec, currentTotal;
function playTime () {
    video.addEventListener("timeupdate", function () {          //현재재생시간
        if (video.duration == video.currentTime) {
            play.style.display = "inline-block"; pause.style.display = "none";
        }
        current = video.currentTime;
        currentmin = Math.floor(current / 60);
        currentsec = Math.floor(current - (currentmin * 60));
        if (currentmin < 10) { currentmin = "0" + currentmin;  }
        if (currentsec < 10) { currentsec = "0" + currentsec;  }
        currentTotal = currentmin + ":" + currentsec;
        timeCurrent.innerHTML = currentTotal;
    });
} 
//프로그레스바
function progressPlayer () {
    video.addEventListener("timeupdate", function () {
        var max = Math.floor(video.duration);
        var current = Math.floor(video.currentTime);
        var percent = 100 * (current / max);
        progressBar.style.width = percent + "%";
    });
}

//프로그레스바 seek
function seek (event) {
    var seekTotal = parseInt(progress.style.width);
    var seekX = event.offsetX;
    var seekPercent = 100 * (seekX / seekTotal); 
    progressBar.style.width = seekPercent + "%";
    var seekMove = (seekPercent / 100) * Math.floor(video.duration);
    video.currentTime = seekMove;
}
/* 파이어폭스 이벤트 크로스브라우징코드 */
if (navigator.userAgent.indexOf("Firefox") >= 0) {
    var eventNames = ["mousedown", "mouseover", "mouseout", "mousemove", "mousedrag", "click", "dbclick", "keydown", "keypress", "keyup"];
    for (var i = 0; i <eventNames.length; i++) {
        window.addEventListener(eventNames[i], function (e) {
            window.event = e;
        }, true);
    }
}
progress.addEventListener("click", function () {
    seek(event);
});//seek