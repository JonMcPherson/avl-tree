pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }
    stage('Build') {
      steps {
        sh 'sbt -mem 256 clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'sbt -mem 256 test'
      }
    }
  }
}