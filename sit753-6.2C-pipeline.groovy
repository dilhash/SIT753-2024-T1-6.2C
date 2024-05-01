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
          emailext(
            to: 'dilumb2024@gmail.com',
            subject: 'Unit/Integration Tests Passed!',
            body: 'Unit and Integration Tests successful!'
          )
        }
        failure {
          emailext(
            to: 'dilumb2024@gmail.com',
            subject: 'Unit/Integration Tests Failed!',
            body: 'Unit and Integration Tests failed!'
          )
        }
      }
    }
    stage('Code Analysis') {
      steps {
        echo 'Analyzing code with SonarQube...'
      }
      post {
        failure {
          emailext(
            to: 'dilumb2024@gmail.com',
            subject: 'Code Analysis Failed!',
            body: 'Code analysis identified issues!'
          )
        }
      }
    }
    stage('Security Scan') {
      steps {
        echo 'BlackDuck Scanning...'
      }
      post {
        failure {
          emailext(
            to: 'dilumb2024@gmail.com',
            subject: 'Security Scan Failed!',
            body: 'Security vulnerabilities found!'
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
    }
    stage('Deploy to Production') {
      steps {
        echo 'Provision AWS Resources in Prod AWS Account..'
        echo 'Deploy Application to Prod AWS..'
      }
    }
  }
}
