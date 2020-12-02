(function(){
    console.log("Hello World!");
})();
$('#submit').click(function() {
    $('.form-signin').each(function() {
        $(this).submit();
    });
});
