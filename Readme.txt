

java -Dbase.url=http://localhost:8081/ -jar Router-exec.jar
java -Dbase.url=http://localhost:8081/ -Denable.request.logs=true -jar Router-exec.jar
java -Dserver.port=2020 -Dbase.url=http://localhost:8081/ -Denable.request.logs=true -jar Router-exec.jar
