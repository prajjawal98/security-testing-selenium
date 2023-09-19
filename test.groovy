pipeline {
    agent any

    stages {
        stage('Get Source Code') {
            steps {
                git branch: 'main', credentialsId: 'c41b4a50-2ae9-4376-ba70-2beca3b3e910', url: 'https://github.com/prajjawal98/security-testing-selenium.git'
                echo 'Hello World'
            }
        }
        stage('Start ZAP') {
            steps {
                script {
                    sh "$ZAPROXY_HOME/zap.sh -daemon -port 8082 -host 0.0.0.0 -config api.disablekey=true"
                }
            }
        }

        stage('Run Selenium Tests') {
            steps {
                script {
                    // Add commands to run your Selenium tests here
                    sh "mvn clean install"
                }
            }
        }

        stage('Stop ZAP') {
            steps {
                script {
                    sh "$ZAPROXY_HOME/zap.sh -shutdown -config api.disablekey=true"
                }
            }
        }
        stage('Publish the Report'){
            steps{
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'target/zap-security-report.html', reportName: 'ZAP Report', reportTitles: '', useWrapperFileDirectly: true])
            }
        }
    }
}
