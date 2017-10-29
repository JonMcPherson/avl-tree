#!groovy

node {
    stage("Checkout") {
        checkout scm
    }
    stage("Build") {
        sbt "clean package"
    }
    stage("Test") {
        sbt "test"
    }
}

def sbt(def args) {
    def sbtHome = tool 'default-sbt'

    sh "${sbtHome}/bin/sbt -Dsbt.log.noformat=true ${args}"
}