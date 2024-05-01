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
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Unit/Integration Tests Passed!", 
                        body: "Unit and Integration Tests successful! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
                failure {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Unit/Integration Tests Failed!", 
                        body: "Unit and Integration Tests failed! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'Analyzing code with SonarQube...' 
            }
            post {
                success {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Code Analysis Passed!", 
                        body: "Code analysis successful Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
                failure {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Code Analysis Failed!", 
                        body: "Code analysis identified issues! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
            }
        }
        stage('Security Scan') {
            steps {
                echo 'BlackDuck Scanning...' 
            }
            post {
                success {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - BlackDuck Scan Passed!", 
                        body: "Security vulnerabilities check passed: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
                failure {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Security Scan Failed!", 
                        body: "Security vulnerabilities found! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
            }
        }
        stage('Deploy to Staging') {
            steps {
                echo 'Provision AWS Resources in QA AWS Account..'
                echo 'Deploy Application to QA AWS..' 
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging environment...' 
            }
            post {
                success {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Integration Tests on Staging Passed!", 
                        body: "Integration tests on staging environment passed! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
                failure {
                    emailext (
                        to: 'dilumb2024@gmail.com', 
                        subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Integration Tests on Staging Failed!", 
                        body: "Integration tests on staging environment failed! Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                        attachLog: true // Attach the build log
                    )
                }
            }
        }
        stage('Deploy to Production') {
            steps {
                echo 'Provision AWS Resources in Prod AWS Account..'
                echo 'Deploy Application to Prod AWS..'
                echo "for autobuild test"
            }
        }
    }
    
    post {
        always {
            // Archive the build log if it exists
            script {
                def buildLogFilePath = "${env.WORKSPACE}/build.log"
                if (fileExists(buildLogFilePath)) {
                    archiveArtifacts artifacts: 'build.log', excludes: ''
                }
            }
            
            // Attach the archived files
            emailext (
                to: 'dilumb2024@gmail.com', 
                subject: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} - Build Log and Other Attachments", 
                body: "Pipeline: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Runtime: ${currentBuild.durationString}",
                attachLog: true // Attach the build log
            )
        }
    }
}
