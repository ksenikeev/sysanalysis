<html>
<head>
    <title>ТСиСА. Практика 0</title>
    <meta charset="utf-8"/>
    <style>
        body {
            font-size: 16px;
            color: #606266;
            border-spacing: 0;
        }
        td {
            align:center;
            padding: 10px;
            text-align: center;
        }
    </style>

        <h2 align="center">Данные блок-чейна</h2>

        <div align="center">

</head>
<body><table>
    <th></th><th>Создан</th><th>Владелец</th><th>Группа</th><th>Объекты</th><th>Морфизмы</th><th>Композиции</th><th>id</th>
    <#list model["chain"] as block>
    <tr>
        <td>${block?counter}</td>
        <td>${block.ts}</td>
        <td>${block.name}</td>
        <td>${block.group}</td>
        <td>${block.objects}</td>
        <td>${block.morphism}</td>
        <td>${block.composition}</td>
        <td>${block.id}</td>
    </tr>
</#list>
</table>

</div>
<br>

<table align="center" >
    <tr>
        <td>
            <a href="/sa/">
            <img src="/sa/resources/img/homepage_home_house_icon_225739.svg" width="128"
                 height="128" alt="Главная">
            </a>
        </td>
        <td>
            <a href="/sa/task">
                <img src="/sa/resources/img/data_page_sheet_file_type_format_folder_document_ui_icon_219962.svg"
                     width="128" height="128" alt="Задание">
            </a>
        </td>
<!--        <td><img src="/resources/img/save_document_user_interface_ux_down_download_ui_icon_219961.svg"
                 width="128" height="128" alt="Отправить данные"></td>
 -->       <td>
            <a href="/sa/info">
                <img src="/sa/resources/img/direction_navigation_gps_pin_location_essential_map_android_ui_icon_219936.svg"
                     width="128" height="128" alt="Информация">
            </a>
        </td>
    </tr>
    <tr>
        <td>Главная</td><td>Задания</td>
<!--
        <td>Отправить данные</td>
-->
        <td>Информация</td>
    </tr>
</table>

</body>
</html>

