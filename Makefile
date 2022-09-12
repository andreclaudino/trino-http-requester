TRINO_VERSION=395
BASE_VERSION=0.0.1
DOCKER_IMAGE_NAME=andreclaudino/trino
DOCKER_IMAGE_TAG=$(TRINO_VERSION)-$(BASE_VERSION)

target/http-requester/http-requester.jar:
	TRINO_VERSION=$(TRINO_VERSION) sbt assembly

docker/http-requester/http-requester.jar: target/http-requester/http-requester.jar
	cp -r target/http-requester docker/http-requester

docker/image: docker/http-requester/http-requester.jar
	docker build -t $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG) docker/ -f docker/Dockerfile --build-arg TRINO_VERSION=$(TRINO_VERSION)
	touch docker/image

docker/push: docker/image
	docker push $(DOCKER_IMAGE_NAME):$(DOCKER_IMAGE_TAG)


clean:
	rm -rf target/http-requester/http-requester.jar
	rm -rf docker/http-requester
	rm -rf docker/image

clean-all: clean
	sbt clean