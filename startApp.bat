@rem Прописать путь к файлу с jar

@echo off
cd /d %~dp0 && "%JAVA_HOME%"\bin\java -jar XmlConverterFromDB.jar
pause