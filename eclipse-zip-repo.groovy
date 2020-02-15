#!groovy

pipeline {
    agent any

    // set eclipse locating and temp folder for repository
    environment {
        dest = 'file:///$WORKSPACE/tmp/$repoName/'
    }

    // basic parameters
    // TO DO : add parameters with secrets in jenkinsfile
    parameters {
        string(name: 'source', defaultValue: 'http://download.eclipse.org/nattable/releases/1.5.0/repository.zip', description: 'Eclipse repo zip file = url')
        string(name: 'repoName', defaultValue: 'nattable/1.5.0', description: 'Desirable path of unzip files after URL')
        string(name: 'sshUsername', defaultValue: 'optimax', description: 'ssh username')
    }

    stages {

        // For download i used eclipce application. reff: https://phauer.com/2015/offline-copy-mirror-eclipse-p2-repository/
        stage('Download repository and unzip repo') {
            steps ('download mirror') {
                script {
                    sh """
                    wget -O repo.zip -P $dest $source
                    cd tmp
                    unzip repo.zip
                    rm repo.zip 
                    """
                }
            }
        }

        // stage ('Push repository') {
        //     // set server ip from credentials.
        //     environment {
        //         destServer = credentials('serverIp')
        //     }

        //     // steps ('Upload repository to server and clear tmp folder.') {
        //     //     script {
        //     //         // use credentials from parameters.
        //     //         sshagent(credentials: ['p2-site-updates']) {

        //     //             sh """
        //     //             ssh-keyscan $destServer >> ~/.ssh/known_hosts
        //     //             scp -r $WORKSPACE/tmp/* $sshUsername@$destServer:/data/update-sites/mirrors/
        //     //             rm -rf $WORKSPACE/tmp/*
        //     //             """
        //     //         }
        //     //     }
        //     // }
        // }
    }
}
