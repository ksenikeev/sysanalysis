<html>
<head>
    <title>Анализ данных. Тест</title>
    <meta charset="utf-8"/>
    <style>
        body {
            font-size: 16px;
            color: #606266;
        }
        ol {
            font-size: 24px;
            color: #606266;
        }
        li {
            padding:5px;
        }
        .task {
            font-size: 24px;
            color: #606266;
        }
        .wo {
            font-size: 24px;
            color: #FF0000;
        }
        td {
            align:center;
            padding: 10px;
            text-align: center;
        }
    </style>
</head>
<body>

<h2 align="center">Информация</h2>


<div class="task">
    При выполнении задания ван необходимо зафиксировать свой выбор в блок-чейне. Сделать это можно:
</div>

<ol>
    <li>
        Для выбора правила отбора отправить GET запрос на http://194.67.67.199/dataanalysis с параметром rule<br>
        Пример: http://194.67.67.199/dataanalysis?rule={"name":"LastName FirstName", "group":"group number","rule":"Исключаются все лица старше 35 лет"}
    </li>
    <li>
        Для выбора региона отправить GET запрос на http://194.67.67.199/dataanalysis с параметром region<br>
        Пример: http://194.67.67.199/dataanalysis?region={"name":"LastName FirstName", "group":"group number","region":"Татарстан"}
    </li>
    <li>
        Используя интерфейс сайта.
    </li>
</ol>

<div class="wo">
    При отправке данных используйте URLEncode !!!
</div>

<div class="task">
    Пример скрипта на Python:
</div>
<br>
<div>
<code>
    import urllib.request<br>
    import urllib.parse<br>
    data=urllib.parse.quote('{"name":"LastName FirstName", "group":"group number","rule":"Исключаются все лица старше 35 лет"}')<br>
    resp = urllib.request.urlopen("http://194.67.67.199/dataanalysis?rule=" + data).read()<br>
    print(resp)<br>
</code>
</div>

<div class="task">
    <a href="/resources/example0-1.xlsx">Файл с данными</a>
</div>

<table align="center" >
    <tr>
        <td>
            <a href="/">
            <img src="/resources/img/homepage_home_house_icon_225739.svg" width="128"
                 height="128" alt="Главная">
            </a>
        </td>
        <td>
            <a href="blockchain">
                <img src="/resources/img/archive_document_cloud_data_folder_backup_website_browser_ui_icon_219918.svg"
                     width="128" height="128" alt="Блок-чейн">
            </a>
        </td>
        <td>
            <a href="task">
                <img src="/resources/img/data_page_sheet_file_type_format_folder_document_ui_icon_219962.svg"
                     width="128" height="128" alt="Задания">
            </a>
        </td>
<!--
        <td><img src="/resources/img/save_document_user_interface_ux_down_download_ui_icon_219961.svg"
                 width="128" height="128" alt="Отправить данные"></td>
-->
    </tr>
    <tr>
        <td>Главная</td><td>Блок-чейн</td>
        <td>Задания</td>
<!--
        <td>Отправить данные</td>
-->
    </tr>
</table>

</body>
</html>

