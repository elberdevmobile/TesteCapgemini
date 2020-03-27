window.androidObj = function AndroidClass(){};

//Variaveis e funcoes
var CaixaTexto = document.createElement("p");
var TextoNativo = document.createTextNode("");
CaixaTexto.appendChild(TextoNativo);
var inputContainer = document.createElement("p");
var input = document.createElement("INPUT");
input.setAttribute("type", "text");
inputContainer.appendChild(input);
var buttonContainer = document.createElement("p");
var button = document.createElement("button");
button.innerHTML = "Enviar para o Android";
button.style.width = "200px";
button.style.height = "60px";
button.addEventListener ("click", function() {
  window.androidObj.textoparaandroid(input.value);
});
buttonContainer.appendChild(button);

document.body.appendChild(CaixaTexto);
document.body.appendChild(inputContainer);
document.body.appendChild(buttonContainer);

//Funcao para atualizar o texto recebido
function updateFromAndroid(message){
    TextoNativo.nodeValue = message;
}
