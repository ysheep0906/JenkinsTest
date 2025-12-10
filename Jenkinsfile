pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'yangshinhee/jenkinstest'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Build with Gradle') {
            steps {
                sh '''
                    cd projects/spring-app
                    chmod +x gradlew
                    ./gradlew clean build
                '''
            }
        }

        stage('Docker Build & Push') {
            environment {
                DOCKER_CREDENTIALS_ID = 'docker-hub-credential'
            }
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                        docker build --no-cache -t $DOCKER_IMAGE:$DOCKER_TAG -f projects/spring-app/Dockerfile projects/spring-app
                        docker push $DOCKER_IMAGE:$DOCKER_TAG
                    '''
                }
            }
        }

        stage('Deploy to app1') {
            steps {
                sshagent (credentials: ['app-ssh-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@172.31.1.106 <<'ENDSSH'
                    PID=$(lsof -ti:8080)
                    if [ ! -z "$PID" ]; then 
                        echo "Killing process using port 8080: $PID"
                        kill -9 "$PID"
                        sleep 2
                    fi
                    docker rm -f app || true
                    docker rmi -f yangshinhee/jenkinstest || true
                    sleep 2
                    docker ps --filter "publish=8080" --format "{{.ID}}" | xargs -r docker rm -f
                    docker run -d --name app -p 8080:8080 yangshinhee/jenkinstest
ENDSSH'''
                }    
            }
        }

        stage('Deploy to app2') {
            steps {
                sshagent (credentials: ['app-ssh-key']) {
                    sh '''
                    ssh -o StrictHostKeyChecking=no ec2-user@172.31.1.238 <<'ENDSSH'
                    PID=$(lsof -ti:8080)
                    if [ ! -z "$PID" ]; then 
                        echo "Killing process using port 8080: $PID"
                        kill -9 "$PID"
                        sleep 2
                    fi
                    docker rm -f app || true
                    docker rmi -f yangshinhee/jenkinstest || true
                    sleep 2
                    docker ps --filter "publish=8080" --format "{{.ID}}" | xargs -r docker rm -f
                    docker run -d --name app -p 8080:8080 yangshinhee/jenkinstest
ENDSSH'''
                }    
            }
        }
    }
}
