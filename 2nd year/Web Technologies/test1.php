<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <ul>
    <?php
        $dir = new DirectoryIterator(dirname('.'));
        foreach ($dir as $fileinfo) {
            $path = $fileinfo;
            $ext = pathinfo($path, PATHINFO_EXTENSION);
            if ($ext=='img'){
                echo("<li style=text_align=center;>"+pathinfo($path,PATHINFO_BASENAME)+"</li>\n");
            }
        }
    ?>
    </ul>
</body>
</html>