pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Microsoft\\jdk-17.0.20.101-hotspot'
        PATH = "C:\\Users\\niush\\AppData\\Local\\Apache\\maven\\bin;${JAVA_HOME}\\bin;${PATH}"
        JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8'
    }

    stages {
        stage('Build') {
            steps {
                bat 'chcp 65001 > nul && mvn compile'
            }
        }

        stage('Test') {
            steps {
                bat 'chcp 65001 > nul && mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'chcp 65001 > nul && mvn package -DskipTests'
            }
        }
    }
}
