pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building code with Maven...' 
            }
        }
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running unit tests with JUnit...' 
                echo 'Running integration tests with Selenium...' 
            }
            post {
                success {
                    emailaction(recipient: 's220459698@deakin.edu.au', subject: 'Unit/Integration Tests Passed!', body: 'Unit and Integration Tests successful!', attachment: '**/test-results.xml') 
                }
                failure {

                    emailaction(recipient: 's220459698@deakin.edu.au', subject: 'Unit/Integration Tests Failed!', body: 'Unit and Integration Tests failed!', attachment: '**/test-results.xml') 
                }
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'Analyzing code with SonarQube...' // Replace with your code analysis tool
            }
            post {
                failure {
                    emailaction(recipient: 's220459698@deakin.edu.au', subject: 'Code Analysis Failed!', body: 'Code analysis identified issues!', attachment: '**/sonar-report.xml') 
                }
            }
        }
        stage('Security Scan') {
            steps {
                echo 'Scanning for vulnerabilities with SAST scanner...' 
            }
            post {
                failure {
                    emailaction(recipient: 'youremail@example.com', subject: 'Security Scan Failed!', body: 'Security vulnerabilities found!', attachment: '**/security-scan-report.txt') // Update recipient address and attachment path
                }
            }
        }
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying application to staging server...' 
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging environment...' 
            }
        }
        stage('Deploy to Production') {
            steps {
                echo 'Deploying application to production server...' 
            }
        }
    }
}
