package com.example.server.grpc;

//import java.util.concurrent.ExecutionException;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthTokenServiceGrpc {

  private AuthTokenServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.example.auth.AuthTokenService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.server.grpc.AccessTokenRequest,
      com.example.server.grpc.AuthResponse> getParseTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "parseToken",
      requestType = com.example.server.grpc.AccessTokenRequest.class,
      responseType = com.example.server.grpc.AuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.server.grpc.AccessTokenRequest,
      com.example.server.grpc.AuthResponse> getParseTokenMethod() {
    io.grpc.MethodDescriptor<com.example.server.grpc.AccessTokenRequest, com.example.server.grpc.AuthResponse> getParseTokenMethod;
    if ((getParseTokenMethod = AuthTokenServiceGrpc.getParseTokenMethod) == null) {
      synchronized (AuthTokenServiceGrpc.class) {
        if ((getParseTokenMethod = AuthTokenServiceGrpc.getParseTokenMethod) == null) {
          AuthTokenServiceGrpc.getParseTokenMethod = getParseTokenMethod =
              io.grpc.MethodDescriptor.<com.example.server.grpc.AccessTokenRequest, com.example.server.grpc.AuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "parseToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.server.grpc.AccessTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.server.grpc.AuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthTokenServiceMethodDescriptorSupplier("parseToken"))
              .build();
        }
      }
    }
    return getParseTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthTokenServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceStub>() {
        @java.lang.Override
        public AuthTokenServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthTokenServiceStub(channel, callOptions);
        }
      };
    return AuthTokenServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static AuthTokenServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceBlockingV2Stub>() {
        @java.lang.Override
        public AuthTokenServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthTokenServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return AuthTokenServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthTokenServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceBlockingStub>() {
        @java.lang.Override
        public AuthTokenServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthTokenServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthTokenServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthTokenServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthTokenServiceFutureStub>() {
        @java.lang.Override
        public AuthTokenServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthTokenServiceFutureStub(channel, callOptions);
        }
      };
    return AuthTokenServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void parseToken(com.example.server.grpc.AccessTokenRequest request,
        io.grpc.stub.StreamObserver<com.example.server.grpc.AuthResponse> responseObserver)  {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getParseTokenMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AuthTokenService.
   */
  public static abstract class AuthTokenServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AuthTokenServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AuthTokenService.
   */
  public static final class AuthTokenServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AuthTokenServiceStub> {
    private AuthTokenServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthTokenServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthTokenServiceStub(channel, callOptions);
    }

    /**
     */
    public void parseToken(com.example.server.grpc.AccessTokenRequest request,
        io.grpc.stub.StreamObserver<com.example.server.grpc.AuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getParseTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AuthTokenService.
   */
  public static final class AuthTokenServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<AuthTokenServiceBlockingV2Stub> {
    private AuthTokenServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthTokenServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthTokenServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.example.server.grpc.AuthResponse parseToken(com.example.server.grpc.AccessTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getParseTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service AuthTokenService.
   */
  public static final class AuthTokenServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AuthTokenServiceBlockingStub> {
    private AuthTokenServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthTokenServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthTokenServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.server.grpc.AuthResponse parseToken(com.example.server.grpc.AccessTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getParseTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AuthTokenService.
   */
  public static final class AuthTokenServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AuthTokenServiceFutureStub> {
    private AuthTokenServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthTokenServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthTokenServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.server.grpc.AuthResponse> parseToken(
        com.example.server.grpc.AccessTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getParseTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PARSE_TOKEN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PARSE_TOKEN:
          serviceImpl.parseToken((com.example.server.grpc.AccessTokenRequest) request,
              (io.grpc.stub.StreamObserver<com.example.server.grpc.AuthResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getParseTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.server.grpc.AccessTokenRequest,
              com.example.server.grpc.AuthResponse>(
                service, METHODID_PARSE_TOKEN)))
        .build();
  }

  private static abstract class AuthTokenServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthTokenServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.server.grpc.AuthProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthTokenService");
    }
  }

  private static final class AuthTokenServiceFileDescriptorSupplier
      extends AuthTokenServiceBaseDescriptorSupplier {
    AuthTokenServiceFileDescriptorSupplier() {}
  }

  private static final class AuthTokenServiceMethodDescriptorSupplier
      extends AuthTokenServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AuthTokenServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthTokenServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthTokenServiceFileDescriptorSupplier())
              .addMethod(getParseTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
