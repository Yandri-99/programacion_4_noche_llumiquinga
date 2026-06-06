from rest_framework import serializers
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from .models import Driver

class EmailTokenObtainPairSerializer(TokenObtainPairSerializer):
    username_field = 'email'

    def validate(self, attrs):
        from django.contrib.auth import get_user_model
        from rest_framework_simplejwt.exceptions import AuthenticationFailed
        User = get_user_model()
        email = attrs.get('email')
        password = attrs.get('password')
        try:
            user = User.objects.get(email=email)
        except User.DoesNotExist:
            raise AuthenticationFailed('No active account found with the given credentials')
        if not user.check_password(password):
            raise AuthenticationFailed('No active account found with the given credentials')
        if not user.is_active:
            raise AuthenticationFailed('User account is disabled')
        refresh = self.get_token(user)
        data = {
            'access': str(refresh.access_token),
            'refresh': str(refresh),
            'user_id': user.id,
            'email': user.email,
            'nombre': user.get_full_name() or user.username,
            'is_staff': user.is_staff,
        }
        return data

class DriverSerializer(serializers.ModelSerializer):
    nombre = serializers.SerializerMethodField()

    class Meta:
        model = Driver
        fields = ['id', 'email', 'username', 'nombre', 'telefono', 'licencia', 'disponible', 'is_active', 'date_joined']
        read_only_fields = ['date_joined']

    def get_nombre(self, obj):
        return obj.get_full_name() or obj.username

class RegisterSerializer(serializers.ModelSerializer):
    nombre = serializers.SerializerMethodField()
    password = serializers.CharField(write_only=True, min_length=8)
    password2 = serializers.CharField(write_only=True, min_length=8)

    class Meta:
        model = Driver
        fields = ['email', 'username', 'nombre', 'password', 'password2']
        extra_kwargs = {'username': {'required': False}}

    def get_nombre(self, obj):
        return obj.get_full_name() or obj.username

    def validate(self, data):
        if data.get('password') != data.get('password2'):
            raise serializers.ValidationError({'password2': 'Las contraseñas no coinciden'})
        return data

    def create(self, validated_data):
        validated_data.pop('password2')
        password = validated_data.pop('password')
        username = validated_data.pop('username', None)
        email = validated_data.get('email', '')
        if not username:
            username = email.split('@')[0] or f"user_{Driver.objects.count() + 1}"
        validated_data['username'] = username
        driver = Driver(**validated_data)
        driver.set_password(password)
        driver.save()
        return driver

class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField()

class DriverStatsSerializer(serializers.Serializer):
    total = serializers.IntegerField()
    available = serializers.IntegerField()
    unavailable = serializers.IntegerField()
