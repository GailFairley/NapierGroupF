# Using the latest MySQL image
FROM mysql
# Setting up the working directory
WORKDIR /tmp
# Copy all the files to the working directory of the container
COPY world.sql /tmp/
# Copy the main SQL file to docker-entrypoint-initdb.d.
COPY world.sql /docker-entrypoint-initdb.d
# Set the root password
ENV MYSQL_ROOT_PASSWORD world