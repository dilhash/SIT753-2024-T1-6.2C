pipeline {
    agent any

    environment {
        DIRECTORY_PATH = 'path/to/your/code'  // Replace with your actual directory path
        TESTING_ENVIRONMENT = 'QA1'
        PRODUCTION_ENVIRONMENT = "${env.USER}" // Uses your username for production
    }

    stages {
        stage('Build') {
            steps {
                echo 'Fetching the source code from the directory path specified by the environment variable.'
                echo 'Compiling the code and generating any necessary artifacts.'
            }
        }
        stage('Test') {
            steps {
                echo 'Running unit tests.'
                echo 'Running integration tests.'
            }
        }
        stage('Code Quality Check') {
            steps {
                echo 'Checking the quality of the code.'
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying the application to a testing environment specified by the environment variable: ${TESTING_ENVIRONMENT}"
            }
        }
        stage('Approval') {
            steps {
                echo 'Pausing for manual approval...'
                sleep 10
            }
        }
        stage('Deploy to Production') {
            steps {
                echo "Deploying the code to the production environment: ${PRODUCTION_ENVIRONMENT}"
            }
        }
    }
}
