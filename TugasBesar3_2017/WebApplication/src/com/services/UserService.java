/**
 * UserService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.services;

public interface UserService extends java.rmi.Remote {
    public com.services.User getUserWithoutValidation(int arg0) throws java.rmi.RemoteException;
    public com.services.User getPreferredDriver(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, int arg4) throws java.rmi.RemoteException, com.services.TokenException;
    public com.services.ArrayList loadPreferredLocations(java.lang.String arg0, com.services.User arg1) throws java.rmi.RemoteException, com.services.TokenException;
    public com.services.User getUser(java.lang.String arg0, int arg1) throws java.rmi.RemoteException, com.services.TokenException;
    public com.services.User[] getDriver(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3) throws java.rmi.RemoteException, com.services.TokenException;
    public com.services.User getUserByToken(java.lang.String arg0, int arg1) throws java.rmi.RemoteException, com.services.TokenException;
    public java.lang.String getValidation(java.lang.String arg0, int arg1) throws java.rmi.RemoteException;
    public boolean saveUser(java.lang.String arg0, com.services.User arg1) throws java.rmi.RemoteException, com.services.TokenException;
    public int createUser(java.lang.String arg0, com.services.User arg1) throws java.rmi.RemoteException, com.services.TokenException;
    public com.services.User getUserForOrder(java.lang.String arg0, int arg1, int arg2) throws java.rmi.RemoteException, com.services.TokenException;
}
