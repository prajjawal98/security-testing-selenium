pipeline {
    agent any
 
    stages {
        stage('Run ZAP in Detached Mode') {
            steps {
                script {
                    // Define your ZAP installation path
                    def zapPath = "/var/lib/jenkins/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/ZAP_2.7.0/ZAP_2.7.0/zap.sh"  // Modify this with the actual path to your ZAP installation
                    
                    // Start ZAP in detached mode
                    sh "nohup ${zapPath} -daemon -host 0.0.0.0 -port 8082 > zap.log 2>&1 &"
                    
                    // Wait for ZAP to start (you may need to adjust the sleep duration)
                    sleep(time: 60, unit: 'SECONDS')
                    
                    // Check if ZAP has started (example command to check ZAP's API)
                    def zapStarted = sh(script: "curl -s http://localhost:8082/JSON/core/view/version", returnStatus: true)
                    
                    if (zapStarted == 0) {
                        echo "ZAP has started successfully"
                    } else {
                        error "ZAP failed to start"
                    }
                }
            }
        }
        stage('Checkout Git Repository'){
            steps{
                script{
                    git branch: 'main', credentialsId: 'c41b4a50-2ae9-4376-ba70-2beca3b3e910', url: 'https://github.com/prajjawal98/security-testing-selenium.git'
                    echo 'Git Checkout has completed'
                }
            }
        }
        stage('Execute ZAP Tests'){
            steps{
                script{
                    sh 'mvn clean install'
                }
            }
        }
        stage('ShutDown the ZAP'){
            steps{
                script{
                    sh "curl http://localhost:8082/JSON/core/action/shutdown/"
                    
                }
            }
        }
        stage('Publish the HTML Report'){
            steps{
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '', reportFiles: 'target/zap-security-report.html', reportName: 'ZAP Report', reportTitles: 'ZAP SCAN REPORT FOR SELENIUM TEST', useWrapperFileDirectly: true])

            }
        }
        
    }
}
