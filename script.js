
function Telinha(edit_tela) {
    var display = document.getElementById(edit_tela).style.display;
    if(display == "block")
        document.getElementById(edit_tela).style.display = 'none';
    else
        document.getElementById(edit_tela).style.display = 'block';
}

