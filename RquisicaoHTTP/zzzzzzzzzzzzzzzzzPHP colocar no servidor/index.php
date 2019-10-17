<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
require_once "UploadImagem.php";
$img_path = new UploadImagem('imagem', __DIR__, 300);
var_dump($_POST);
}

?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Dejota</title>
  </head>
  <body>

  <?php
  function listar_caminhos($diretorio, &$todos_caminho=[]) {
      $arquivos_ignorados = array(".", "..", "index.php", "UploadImagem.php");
      $lista_arquivos_diretorios = scandir($diretorio);
      foreach($lista_arquivos_diretorios as $arquivo) {
          $caminho = $arquivo;
          if (!in_array($arquivo, $arquivos_ignorados)) {
              if (is_file($caminho) && is_readable($caminho)) {
                  $todos_caminho[] = $caminho;
              } elseif (is_dir($caminho) && is_readable($caminho)) {
                  listar_caminhos($caminho, $todos_caminho);
              }
          }
      }
      return $todos_caminho;
  }

    $caminhos = listar_caminhos(__DIR__);
    foreach ($caminhos as $caminho):
      ?> <img src="<?php echo $caminho; ?>" alt="img"> <?php
    endforeach;
   ?>



  </body>
</html>
