FROM eclipse-mosquitto
COPY . /mosquitto/config
ARG ADMIN_USERNAME=dynsec
ARG ADMIN_PASS=Dynsec@123
RUN /bin/sh -c '/usr/bin/mosquitto_ctrl dynsec init /mosquitto/config/dynamic-security.json ${ADMIN_USERNAME} ${ADMIN_PASS}; \
                chmod 777 /mosquitto/config/dynamic-security.json'
ENTRYPOINT /bin/sh -c '/usr/sbin/mosquitto -c /mosquitto/config/mosquitto.conf'