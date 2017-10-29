pipeline {
    agent any

    stages {
        stage("Checkout") {
            steps {
                checkout scm
            }
        }
        stage("Build") {
            steps {
                sbt "clean package"
            }
        }
        stage("Test") {
            steps {
                sbt "test"
            }
        }
    }
}

def sbt(def args) {
    def sbtHome = tool 'default-sbt'

    sh "${sbtHome}/bin/sbt -Dsbt.log.noformat=true ${args}"
}