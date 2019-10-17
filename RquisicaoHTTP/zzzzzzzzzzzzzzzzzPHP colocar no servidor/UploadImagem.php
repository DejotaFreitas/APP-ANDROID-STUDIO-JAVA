<?php

class UploadImagem {

  private $src;

  function __construct($nameTag, $destino, $dimensao_max=300, $largura=null, $altura=null){

    if ( isset($_FILES[$nameTag]['name']) && $_FILES[$nameTag]['error'] == 0 ) {

      $tamnhoMaximo = 1024 * 1024 * 2;
      if ($_FILES[ $nameTag ][ 'size' ] <= $tamnhoMaximo) {
          $diretorio = $_FILES[ $nameTag ][ 'tmp_name' ];
          $nome = $_FILES[ $nameTag ][ 'name' ];
          $extensao = strtolower(pathinfo($nome, PATHINFO_EXTENSION));

          if (strstr('.jpg;.jpeg;.gif;.png', $extensao)) {

              $novoNome = uniqid(time());
              $this->criar_direotrio($destino);
              $this->src = $this->redimensionar($diretorio, $destino, $novoNome, $extensao, $dimensao_max, $largura, $altura);

          } else { throw new Exception('Enviar apenas imagens jpg, jpeg, png ou gif.'); }
      } else { throw new Exception('Envie arquivo de no máximo 2 Megabytes.'); }
    } else {  throw new Exception('Nenhuma imagem enviada.'); }
  }


  //=====================================================================

  private function criar_direotrio($dir){
    if(!is_dir($dir)):
      mkdir($dir, 0777, true);
    endif;
  }

  //=======================================================================


  private function redimensionar($diretorio_fonte, $diretorio_destino, $nome_aquivo, $extensao, $dimensao_max=300, $largura=null, $altura=null){

    $src = $diretorio_destino.'/'.$nome_aquivo.'.'.$extensao;

      switch ($extensao):
        case 'jpg':
          copy($diretorio_fonte, $diretorio_fonte.'.'.$extensao);
          unlink($diretorio_fonte);
          $imagem_temp = imagecreatefromjpeg($diretorio_fonte.'.'.$extensao);
          break;
        case 'jpeg': $imagem_temp = imagecreatefromjpeg($diretorio_fonte);  break;
        case 'png':  $imagem_temp = imagecreatefrompng($diretorio_fonte);   break;
        case 'gif':  $imagem_temp = imagecreatefromgif($diretorio_fonte);   break;
        default: throw new Exception('Use imagens com seguintes formatos: jpg, jpeg, png ou gif.');
      endswitch;

      $largura_original = imagesx($imagem_temp);
      $altura_original = imagesy($imagem_temp);

      if ($dimensao_max != null) {

        if ($largura_original > $altura_original) {
          $nova_largura = $dimensao_max;
          $nova_altura = floor(($altura_original*$nova_largura)/$largura_original);
        } else {
          $nova_altura = $dimensao_max;
          $nova_largura = floor(($largura_original*$nova_altura)/$altura_original);
        }

      } elseif ($largura != null && $altura == null) {
        $nova_largura = $largura;
        $nova_altura = floor(($altura_original*$nova_largura)/$largura_original);

      } elseif ($largura == null && $altura != null) {
        $nova_altura = $altura;
        $nova_largura = floor(($largura_original*$nova_altura)/$altura_original);
        
      } elseif ($largura != null && $altura != null) {
        $nova_largura = $largura;
        $nova_altura = $altura;
      }

      $imagem_redimensionada = imagecreatetruecolor($nova_largura, $nova_altura);
      imagecopyresampled($imagem_redimensionada, $imagem_temp, 0, 0, 0, 0, $nova_largura, $nova_altura, $largura_original, $altura_original);

      switch ($extensao):
        case 'jpeg': imagejpeg($imagem_redimensionada, $src, 100);   break;
        case 'jpg':  imagejpeg($imagem_redimensionada, $src, 100);   break;
        case 'png':  imagepng($imagem_redimensionada, $src, 1);      break;
        case 'gif':  imagegif($imagem_redimensionada, $src);         break;
        default: throw new Exception('Use formatos de imagem: jpg, jpeg, png ou gif');  break;
      endswitch;

      imagedestroy($imagem_temp);
      imagedestroy($imagem_redimensionada);
      return $src;
  }

  public function __toString(){
    return $this->src;
  }

}
//fim classe
