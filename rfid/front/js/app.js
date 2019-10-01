const inputCodigo = document.querySelector("#codigoCartao");
const video = document.querySelector("#tagVideo");
//const img = document.querySelector("#tagImagem")


inputCodigo.focus();

inputCodigo.addEventListener("keyup", function (event) {
    if (event.which === 13) {
        video.pause();
        video.setAttribute("src", "arquivos/" + inputCodigo.value);
        video.load();
        video.play();
        //img.setAttribute("src", "Arquivos/" + inputCodigo.value + ".jpg");
        //img.load();
        inputCodigo.value = "";
    }
});

function blockSubmit(event) {
    event.preventDefault();
}
