$(document).ready(function () {

    let emailPattern = /^[a-zA-Z0-9]{4,}@(.+)\.{1}(.){2,}$/gi;
    let pswPattern = /[0-9]{1,}|[^0-9a-zA-Z]{1,}/gi;

    /* By default when page loads */
    $(".rightPanel").css("display", "none");
    $(".rightPanel").css("width", "0%");
    $(".leftPanel").css("width", "100%");
    $(".form-container p").eq(0).hide();

    /* When use clicks on Login form button */
    $(".showBtn").click(function () {

        $(".leftPanel .showBtn").fadeOut(800);
        $(".leftPanel").animate({ width: "-=40%" }, 400);

        $(".rightPanel").animate({ width: "+=40%" }, 400);
        $(".rightPanel").slideDown(400);
        $(".temp").css("display", "block");

    });

    /* Cancel button click */
    $(".cancel").click(function () {
        $(".temp").hide();
        $(".leftPanel .showBtn").fadeIn(800);
        $(".leftPanel").animate({ width: "+=40%" }, 400);
        $(".rightPanel").animate({ width: "-=40%" }, 400);
        $(".rightPanel").slideUp(400);
        $(".form-container p").eq(0).text("Login").css("color", "rgb(231, 231, 231, 80%)");
        $("label[for='email'], label[for='psw']").css("color", "white");
        $("label[for='email']").html("E-mail");
        $("label[for='psw']").html("Password");
    });

    $(".btn").mousemove(function (event) {
        var btnX = event.offsetX;
        var xBtn = (btnX * 100) / $(this).width();

        $(this).css("background", "linear-gradient(to right, rgb(14, 209, 69, 55%) "
            + (xBtn - 30) + "%, rgb(14, 209, 69, 80%) " + xBtn + "%, rgb(14, 209, 69, 55%) " + (xBtn + 30) + "%)");
    });

    $(".btn").mouseout(function (event) {
        $(this).css("background", "rgb(14, 209, 69, 60%)");
    });

    $(".btn").mousedown(function (event) {
        $(this).css("background", "rgb(14, 209, 69, 70%)");
    });

    $(".btn").mouseup(function (event) {
        $(this).css("background", "rgb(14, 209, 69, 60%)");
    });

    $(".cancel").mousemove(function (event) {
        var btnX = event.offsetX;
        var xBtn = (btnX * 100) / $(this).width();

        $(this).css("background", "linear-gradient(to right, rgb(236, 28, 36, 55%) "
            + (xBtn - 30) + "%, rgb(236, 28, 36, 80%) " + xBtn + "%, rgb(236, 28, 36, 55%) " + (xBtn + 30) + "%)");
    });

    $(".cancel").mouseout(function (event) {
        $(this).css("background", "rgb(236, 28, 36, 60%)");
    });

    $(".cancel").mousedown(function (event) {
        $(this).css("background", "rgb(236, 28, 36, 80%)");
    });

    $(".cancel").mouseup(function (event) {
        $(this).css("background", "rgb(236, 28, 36, 60%)");
    });

    $("input").mousemove(function (event) {

        var btnX = event.offsetX;
        //var btnY = event.offsetY;
        var xBtn = (btnX * 100) / $(this).width();
        //var xBorder = (btnX * 100) / $(this).offsetWidth;

        $(this).css("background", "linear-gradient(to right, rgb(195, 195, 195, 35%) "
            + (xBtn - 30) + "%, rgb(195, 195, 195, 60%) " + xBtn + "%, rgb(195, 195, 195, 35%) " + (xBtn + 30) + "%)");

    });

    $("input").mouseout(function (event) {
        $(this).css("background", "rgb(195, 195, 195, 30%)");
        $(this).css("border-color", "rgb(195, 195, 195, 40%)");
    });

    $("input[type='text']").keyup(function () {

        //$(this).val( " " + $(this).val().trim());

        if($(this).val().length == 0) {
            $("label[for='email']").html( "E-mail" ).css("color", "white");
        } else if (!emailPattern.test($(this).val())) {
            $("label[for='email']").html( "E-mail isn't valid" ).css("color", "rgb(236, 28, 36, 60%)");
        } else {
            $("label[for='email']").html( "E-mail" ).css("color", "white");
        }
    });

    $("input[type='password']").keyup(function () {

        if($(this).val().length == 0) {
            $("label[for='psw']").html( "Password" ).css("color", "white");
        } else if ($(this).val().length < 4) {
            $("label[for='psw']").html("Weak password").css("color", "rgb(236, 28, 36, 60%)");
            $(this).css("border-color", "rgb(236, 28, 36," + (($(this).val().length > 0) ? 100 / $(this).val().length : 100) + "%)");
        } else if ($(this).val().length < 8) {
            $(this).css("border-color", "rgb(255, 202, 24, 70%)");
            $("label[for='psw']").html("Weak password").css("color", "rgb(255, 202, 24, 60%)");
        } else {
            $("label[for='psw']").html("Password").css("color", "white");
            $(this).css("border-color", "rgb(195, 195, 195, 40%)");
        }
    });

    $(".form-container").mousemove(function () {
        $(".temp").css("opacity", "0.7");
    })

    $(".form-container").mouseout(function () {
        $(".temp").css("opacity", "0.0");
    })
        
    $("form").submit(function (event) {
        
        let email = $("input[type='text']").val();
        let pass = $("input[type='password']").val();

        if (!emailPattern.test(email)) {
            showError();
            return false;
        } else if (!pswPattern.test(pass)) {
            showError();
            return false;
        } else if ($("input[type='password']").val().length < 7) {
            showError();
            return false;
        }

        let vari = $.ajax({
            type: "GET",
            url: "http://127.0.0.1:5500/login.json",
            dataType: "json",
            async: false,
            success: function (data) {

                if (data.email == email && data.password == pass) {
                    //alert("Successfully login with " + data.email + " and " + data.password);
                    //readJson(email, pass);
                    //magic(true);
                    return true;
                } else {
                    alert("Login unsuccessful. You have entered invalid data");
                    event.preventDefault();
                }
            }
        });
    });

    function showError() {
        $(".form-container p").eq(0).html("<b style='color: rgb(236, 28, 36, 80%);'> Wrong input </b>").slideDown("slow");
        $(".form-container p").eq(0).slideUp(1200);
    }

});