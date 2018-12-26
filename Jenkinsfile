#!/usr/bin/env groovy
@Library('cdl@master') _

pipeline {
    agent {
        label 'master'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '50'))
        timeout(time: 10, unit: 'MINUTES')
        disableConcurrentBuilds()
        timestamps()
    }
    environment {
        APP_NAME = 'USER'
        SOURCE_JOB = "${JOB_NAME}"
        BUILD_REPO_PATH = "/var/deploy"
    }
    stages {
        stage('Assembly') {
            steps {
                echo "success"
            }
        }

        stage('Copy Assembly to build repo') {
            steps {
                script {
                    def date = new Date().format('yyyyMMddhhmmssSSS')
                    echo date.toString()
/*                    sh """
if [ ! -d ${env.BUILD_REPO_PATH}/user ]; then
    mkdir ${env.BUILD_REPO_PATH}/user
fi
yes | cp -rf ./target/user*exec.jar ${env.BUILD_REPO_PATH}/user/user.jar
yes | cp -rf user.dockerfile ${env.BUILD_REPO_PATH}/user
yes | cp -rf docker-compose.yml ${env.BUILD_REPO_PATH}/user
"""
*/
                    currentBuild.rawBuild.displayName = date
                }
            }
        }
        stage('Build Docker images') {
            when { branch 'master' }
            steps {
                script {
                    echo 'building docker iamges'
                    
                }
            }
            post {
                failure {
                    echo "BUILDING of ${env.APP_NAME} images FAILED !"
                }
            }
        }
        stage('Deployment') {
            when { branch 'master' }
            steps {
                script {
                    echo "Deployment is skipped"
                }
            }
        }
    }
    post {
        failure {
            echo 'pipeline failed'
        }
    }
}
