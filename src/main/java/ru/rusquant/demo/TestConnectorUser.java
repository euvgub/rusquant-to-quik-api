package ru.rusquant.demo;

import ru.rusquant.api.impl.J2QuikConnector;
import ru.rusquant.data.quik.*;
import ru.rusquant.data.quik.dataframe.TradesDataFrame;
import ru.rusquant.data.quik.descriptor.DatasourceDescriptor;
import ru.rusquant.data.quik.descriptor.ParameterDescriptor;
import ru.rusquant.data.quik.descriptor.QuotesDescriptor;
import ru.rusquant.data.quik.table.Order;
import ru.rusquant.data.quik.types.*;
import ru.rusquant.messages.request.body.RequestSubject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Just for testing
 * Created by kutergin on 30.09.2016.
 */
public class TestConnectorUser {

    private static SecureRandom random = new SecureRandom();

    private static ParameterDescriptor parameterDescriptor;
    private static QuotesDescriptor quotesDescriptor;
    private static DatasourceDescriptor datasourceDescriptor;

    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        J2QuikConnector connector = new J2QuikConnector();


        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter any key to try to connect or type exit");

            String command = reader.readLine();
            if ("exit".equals(command)) {
                isExit = true;
            } else {
                if (connector.connect()) {
                    showTestsMenu(connector, reader);
                } else {
                    System.out.println(connector.getConnectErrorMessage());
                }
            }
        }

        reader.close();
    }


    private static void showTestsMenu(J2QuikConnector connector, BufferedReader reader) throws IOException {
        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Select the test type: manual or auto or exit");
            String test = reader.readLine();
            if ("manual".equals(test)) {
                runManualTest(connector, reader);
            } else if ("auto".equals(test)) {
                runAutoTest(connector);
            } else if ("exit".equals(test)) {
                isExit = true;
            } else {
                System.out.println("Invalid test type!");
            }
        }
        connector.disconnect();
    }


    private static void runManualTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running manual test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Select the type of information:");
            System.out.println("\techo (get echo from server)");
            System.out.println("\tinfo (get info about terminal)");
            System.out.println("\tisconnected (status of connection between terminal and QUIK-server)");
            System.out.println("\tsendtrans (send test transaction to QUIK-server)");
            System.out.println("\tgetorder (get order from QUIK-server)");
            System.out.println("\tgettrades (get trades for order from QUIK-server)");
            System.out.println("\tgetnumberof (get info about quik table from QUIK-server)");
            System.out.println("\tgettableitem (get item of specified quik table from QUIK-server)");
            System.out.println("\tgettableitems (get all items of specified quik table from QUIK-server)");
            System.out.println("\tgetparamext (get trading parameter of quik current tradings table from QUIK-server)");
            System.out.println("\tsubscribeparameter (subscribe trading parameter of quik current tradings table on QUIK-server)");
            System.out.println("\tunsubscribeparameter (unsubscribe trading parameter of quik current tradings table on QUIK-server)");
            System.out.println("\tgettradedate (get trade date from QUIK-server)");
            System.out.println("\tgetsecurityinfo (get info about security from QUIK-server)");
            System.out.println("\tgetmaxcountoflots (get max count of lots in order from QUIK-server)");
            System.out.println("\tgetclassinfo (get security class info from QUIK-server)");
            System.out.println("\tgetclasseslist (get list of security classes from QUIK-server)");
            System.out.println("\tgetclasssecs (get list of securities within class from QUIK-server)");
            System.out.println("\tsubscribequotes (subscribe for quotes (order book) data on QUIK-server)");
            System.out.println("\tissubscribedquotes (check the status of the subscription for quotes (order book) data on QUIK-server)");
            System.out.println("\tunsubscribequotes (subscribe for quotes (order book) data on QUIK-server)");
            System.out.println("\tgetquotes (get order book data from QUIK-server)");
            System.out.println("\tcreateds (get create OHLC datasource at QUIK-server)");
            System.out.println("\tdssize (get current size of the OHLC datasource)");
            System.out.println("\tcloseds (get OHLC datasource at QUIK-server)");
            System.out.println("\tgetohlcprice (get single OHLC ptice (Candle) from QUIK-server)");
            System.out.println("\tgetohlcprices (get all OHLC ptices (Candle) from QUIK-server)");
            System.out.println("\tgetdepo (get info about security limits)");
            System.out.println("\tgetmoney (get info about money limits)");
            System.out.println("\texit");
            System.out.println();

            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else if ("echo".equals(message)) {
                    runEchoTest(connector, reader);
                } else if ("info".equals(message)) {
                    runInfoTest(connector);
                } else if ("sendtrans".equals(message)) {
                    runSendTransactionTest(connector, reader);
                } else if ("getorder".equals(message)) {
                    runGetOrderTest(connector, reader);
                } else if ("gettrades".equals(message)) {
                    runGetTradesTest(connector, reader);
                } else if ("getnumberof".equals(message)) {
                    runQuikTableInfoTest(connector, reader);
                } else if ("gettableitem".equals(message)) {
                    runQuikTableItemTest(connector, reader);
                } else if ("gettableitems".equals(message)) {
                    runQuikTableItemsTest(connector, reader);
                } else if ("getparamext".equals(message)) {
                    runTradingParameterTest(connector, reader);
                } else if ("subscribeparameter".equals(message)) {
                    runSubscribeParameterTest(connector, reader);
                } else if ("unsubscribeparameter".equals(message)) {
                    runUnsubscribeParameterTest(connector);
                } else if ("gettradedate".equals(message)) {
                    runTradeDateTest(connector);
                } else if ("getsecurityinfo".equals(message)) {
                    runSecurityInfoTest(connector, reader);
                } else if ("getmaxcountoflots".equals(message)) {
                    runMaxCountOfLotsTest(connector, reader);
                } else if ("getclassinfo".equals(message)) {
                    runSecurityClassInfoTest(connector, reader);
                } else if ("getclasseslist".equals(message)) {
                    runClassesListTest(connector);
                } else if ("getclasssecs".equals(message)) {
                    runClassSecuritiesTest(connector, reader);
                } else if ("subscribequotes".equals(message)) {
                    runSubscribeQuotesTest(connector, reader);
                } else if ("issubscribedquotes".equals(message)) {
                    runIsSubscribedToQuotesTest(connector);
                } else if ("unsubscribequotes".equals(message)) {
                    runUnsubscribeQuotesTest(connector);
                } else if ("getquotes".equals(message)) {
                    runGetQuotesTest(connector, reader);
                } else if ("createds".equals(message)) {
                    runCreateDatasourceTest(connector, reader);
                } else if ("dssize".equals(message)) {
                    runDatasourceSizeTest(connector);
                } else if ("closeds".equals(message)) {
                    runCloseDatasourceTest(connector);
                } else if ("getohlcprice".equals(message)) {
                    runSingleCandleTest(connector, reader);
                } else if ("getohlcprices".equals(message)) {
                    runAllCandlesSetTest(connector, reader);
                } else if ("getdepo".equals(message)) {
                    runGetDepoTest(connector, reader);
                } else if ("getmoney".equals(message)) {
                    runGetMoneyTest(connector, reader);
                } else if ("isconnected".equals(message)) {
                    QuikDataObject result = connector.isConnected();
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        if (((ConnectionState) result).isConnected().equals(1)) {
                            System.out.println();
                            System.out.println("Terminal is connected to QUIK-server");
                            System.out.println();
                        } else {
                            System.out.println();
                            System.out.println("Terminal is not connected to QUIK-server");
                            System.out.println();
                        }
                    }
                } else {
                    System.out.println("Invalid type of request!");
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }

    }


    private static void runEchoTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running echo test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter not empty message message or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getEcho(message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(((QuikEcho) result).getEchoAnswer());
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runInfoTest(J2QuikConnector connector) throws IOException {
        System.out.println("Running info test...");
        System.out.println();
        System.out.println("Name of the parameter: current value (NA - Not Available)");
        System.out.println("-----------------------------------------------------------");
        System.out.println();

        for (InfoParamType type : InfoParamType.values()) {
            String parameterName = type.toString();
            QuikDataObject result = connector.getInfoParam(parameterName);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
                break;
            } else {
                InfoParameter parameter = (InfoParameter) result;
                System.out.println("\t" + parameter.getParameterName() + ": " + parameter.getParameterValue());
            }
        }
        System.out.println();
    }


    private static void runAutoTest(J2QuikConnector connector) {
        System.out.println("Running auto echo test...");
        System.out.println();

        long startTime = System.currentTimeMillis();
        int count = 100000;
        for (int i = 0; i < count; i++) {
            String message = getRandomString();
            if (message != null && !message.isEmpty()) {
                QuikDataObject result = connector.getEcho(message);
                if (result instanceof ErrorObject) {
                    System.out.println(((ErrorObject) result).getErrorMessage());
                    break;
                } else {
                    String answer = ((QuikEcho) result).getEchoAnswer();
                    if (!answer.contains(message)) {
                        System.out.println("Wrong answer received!");
                        break;
                    } else {
                        if (i % (0.10 * count) == 0) {
                            System.out.print("*");
                        }
                    }
                }
            } else {
                System.out.println("Invalid message!");
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\nConnector process " + count + " of messages in " + (endTime - startTime) + " milliseconds");
        System.out.println("With average shipping duration of request: " + connector.getAvgShippingDurationOfRequest(RequestSubject.ECHO));
        System.out.println("With average duration of request processing: " + connector.getAvgDurationOfRequestProcessing(RequestSubject.ECHO));
        System.out.println("With average shipping duration of response: " + connector.getAvgShippingDurationOfResponse(RequestSubject.ECHO));
        System.out.println("With average request response latency: " + connector.getAvgRequestResponseLatency(RequestSubject.ECHO));
        System.out.println();
    }


    private static void runSendTransactionTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running send transaction test test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter not empty message message or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    Transaction transaction = new Transaction();
                    transaction.setAccount("NL0011100043");
                    transaction.setTransId(1500014677L);
                    transaction.setAction(ActionType.NEW_ORDER);
                    transaction.setClassCode("QJSIM");
                    transaction.setSecCode("RTKM");
                    transaction.setOperation(OperationType.BUY);
                    transaction.setType(OrderType.MARKET);
                    transaction.setQuantity(1.0);
                    transaction.setPrice(0.0);
                    transaction.setComment("Test transaction");
                    transaction.setMode(TransactionMode.ON_TRANS_REPLAY);

                    QuikDataObject result = connector.sendTransaction(transaction);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        Transaction replay = (Transaction) result;
                        System.out.println(replay);
                        isExit = true;
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runGetOrderTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running echo test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter number of oder or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    try {
                        QuikDataObject result = connector.getOrder(Long.parseLong(message));
                        if (result instanceof ErrorObject) {
                            System.out.println(((ErrorObject) result).getErrorMessage());
                            isExit = true;
                        } else {
                            System.out.println((Order) result);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid order number! Enter valid number!");
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runGetTradesTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running get trades test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter number of oder or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    try {
                        QuikDataObject result = connector.getTrades(Long.parseLong(message));
                        if (result instanceof ErrorObject) {
                            System.out.println(((ErrorObject) result).getErrorMessage());
                            isExit = true;
                        } else {
                            System.out.println(((TradesDataFrame) result).toString());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid order number! Enter valid number!");
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runQuikTableInfoTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik table info test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter name of quik table or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getNumberOfRows(message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runQuikTableItemTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik table item test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter name of quik table or type exit:");
            String tableName = reader.readLine();

            System.out.println("Enter row index in range [0, rowsCount - 1]:");
            String indexStr = reader.readLine();

            boolean isValid = tableName != null && !tableName.isEmpty();
            isValid = isValid && (indexStr != null && !indexStr.isEmpty());
            if (isValid) {
                if ("exit".equals(tableName) || "exit".equals(indexStr)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getItem(tableName, Integer.parseInt(indexStr));
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runQuikTableItemsTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
		System.out.println("Running Quik table items test...");
		System.out.println();

		boolean isExit = false;
		while (!isExit) {
			System.out.println();
			System.out.println("Enter name of quik table or type exit:");
			String tableName = reader.readLine();

			System.out.println("Enter first index in range [0, rowsCount - 1]:");
			String firstIndexStr = reader.readLine();

			System.out.println("Enter last index in range [0, rowsCount - 1]:");
			String lastIndexStr = reader.readLine();

			boolean isValid = tableName != null && !tableName.isEmpty();
			isValid = isValid && (firstIndexStr != null && !firstIndexStr.isEmpty());
			isValid = isValid && (lastIndexStr != null && !lastIndexStr.isEmpty());
			if (isValid) {
				if ("exit".equals(tableName) || "exit".equals(lastIndexStr)) {
					isExit = true;
				} else {
					QuikDataObject result = connector.getItems(tableName, Integer.parseInt(firstIndexStr), Integer.parseInt(lastIndexStr));
					if (result instanceof ErrorObject) {
						System.out.println(((ErrorObject) result).getErrorMessage());
						isExit = true;
					} else {
						System.out.println(result);
					}
				}
			} else {
				System.out.println("Invalid test type!");
			}
		}
    }


    private static void runTradingParameterTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik table info test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter parameter name or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    String classCode = "QJSIM";
                    String secCode = "RTKM";
                    QuikDataObject result = connector.getParamEx(classCode, secCode, message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runSubscribeParameterTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik subscribe trading parameter test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter parameter name or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    String classCode = "QJSIM";
                    String secCode = "RTKM";
                    QuikDataObject result = connector.subscribeParameter(classCode, secCode, message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        TestConnectorUser.parameterDescriptor = (ParameterDescriptor) result;
                        System.out.println(result);
                        isExit = true;
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runUnsubscribeParameterTest(J2QuikConnector connector) {
        System.out.println("Running Quik unsubscribe trading parameter test...");
        System.out.println();

        if (TestConnectorUser.parameterDescriptor != null) {
            QuikDataObject result = connector.unsubscribeParameter(TestConnectorUser.parameterDescriptor);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Parameter parameterDescriptor is unset. Subscribe parameter first! Exiting...");
        }
    }


    private static void runTradeDateTest(J2QuikConnector connector) {
        QuikDataObject result = connector.getTradeDate();
        if (result instanceof ErrorObject) {
            System.out.println(((ErrorObject) result).getErrorMessage());
        } else {
            System.out.println(result);
        }
    }


    private static void runSecurityInfoTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running security info test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security code (class = QJSIM) or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getSecurityInfo("QJSIM", message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runMaxCountOfLotsTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running security info test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security code or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getMaxCountOfLotsInOrder("BQUOTE", message, "Q3", "S01-00000F00", 10.0, Boolean.TRUE, Boolean.FALSE);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runSecurityClassInfoTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik table info test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security class code or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getClassInfo(message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runClassesListTest(J2QuikConnector connector) {
        QuikDataObject result = connector.getClassesList();
        if (result instanceof ErrorObject) {
            System.out.println(((ErrorObject) result).getErrorMessage());
        } else {
            System.out.println(result);
        }
    }


    private static void runClassSecuritiesTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running class securities test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security class code:");
            String classCode = reader.readLine();

            System.out.println("Enter first index:");
            String firstIndex = reader.readLine();

            System.out.println("Enter last index or type exit:");
            String lastIndex = reader.readLine();

            if (lastIndex != null && !lastIndex.isEmpty()) {
                if ("exit".equals(lastIndex)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getClassSecurities(classCode, Integer.parseInt(firstIndex), Integer.parseInt(lastIndex));
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runSubscribeQuotesTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik subscribe trading parameter test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security code or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    String classCode = "QJSIM";
                    QuikDataObject result = connector.subscribeQuotes(classCode, message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        TestConnectorUser.quotesDescriptor = (QuotesDescriptor) result;
                        System.out.println(result);
                        isExit = true;
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runUnsubscribeQuotesTest(J2QuikConnector connector) {
        System.out.println("Running Quik unsubscribe trading parameter test...");
        System.out.println();

        if (TestConnectorUser.quotesDescriptor != null) {
            QuikDataObject result = connector.unsubscribeQuotes(TestConnectorUser.quotesDescriptor);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Quotes descriptor is unset. Subscribe parameter first! Exiting...");
        }
    }


    private static void runIsSubscribedToQuotesTest(J2QuikConnector connector) {
        System.out.println("Running Quik unsubscribe trading parameter test...");
        System.out.println();

        if (TestConnectorUser.quotesDescriptor != null) {
            QuikDataObject result = connector.isSubscribedToQuotes(TestConnectorUser.quotesDescriptor);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Quotes descriptor is unset. Subscribe parameter first! Exiting...");
        }
    }


    private static void runGetQuotesTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running get order book test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security code or type exit:");
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    String classCode = "QJSIM";
                    QuikDataObject result = connector.getQuoteLevel2(classCode, message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runCreateDatasourceTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running Quik create OHLC datasource test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();

            System.out.println("Enter time scale:");
            String interval = reader.readLine();

            System.out.println("Enter parameter name or type exit:");
            String parameter = reader.readLine();

            if (parameter != null && !parameter.isEmpty()) {
                if ("exit".equals(parameter)) {
                    isExit = true;
                } else {
                    String classCode = "QJSIM";
                    String secCode = "SBER";
                    QuikDataObject result;
                    if ("without".equals(parameter)) {
                        result = connector.createDataSource(classCode, secCode, interval);
                    } else {
                        result = connector.createDataSource(classCode, secCode, interval, parameter);
                    }

                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        TestConnectorUser.datasourceDescriptor = (DatasourceDescriptor) result;
                        System.out.println(result);
                        isExit = true;
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runCloseDatasourceTest(J2QuikConnector connector) {
        System.out.println("Running close datasource test...");
        System.out.println();

        if (TestConnectorUser.datasourceDescriptor != null) {
            QuikDataObject result = connector.closeDatasource(TestConnectorUser.datasourceDescriptor);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Parameter datasourceDescriptor is unset. Create datasource first! Exiting...");
        }
    }


    private static void runDatasourceSizeTest(J2QuikConnector connector) {
        System.out.println("Running datasource size test...");
        System.out.println();

        if (TestConnectorUser.datasourceDescriptor != null) {
            QuikDataObject result = connector.getDatasourceSize(TestConnectorUser.datasourceDescriptor);
            if (result instanceof ErrorObject) {
                System.out.println(((ErrorObject) result).getErrorMessage());
            } else {
                System.out.println(result);
            }
        } else {
            System.out.println("Parameter datasourceDescriptor is unset. Create datasource first! Exiting...");
        }
    }

    private static void runSingleCandleTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running single candle test...");
        System.out.println();

        if (TestConnectorUser.datasourceDescriptor != null) {
            boolean isExit = false;
            while (!isExit) {
                System.out.println();
                System.out.println("Enter valid candle index or type exit:");
                String message = reader.readLine();
                if (message != null && !message.isEmpty()) {
                    if ("exit".equals(message)) {
                        isExit = true;
                    } else {
                        QuikDataObject result = connector.getOHLCPrice(TestConnectorUser.datasourceDescriptor, Long.parseLong(message));
                        if (result instanceof ErrorObject) {
                            System.out.println(((ErrorObject) result).getErrorMessage());
                            isExit = true;
                        } else {
                            System.out.println(result);
                        }
                    }
                } else {
                    System.out.println("Invalid test type!");
                }
            }
        } else {
            System.out.println("Parameter datasourceDescriptor is unset. Create datasource first! Exiting...");
        }
    }


    private static void runAllCandlesSetTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
		System.out.println("Running candles set test...");
		System.out.println();

		if (TestConnectorUser.datasourceDescriptor != null) {
			boolean isExit = false;
			while (!isExit) {
				System.out.println();
				System.out.println("Enter first index:");
				String firstIndex = reader.readLine();

				System.out.println("Enter last index or type exit:");
				String lastIndex = reader.readLine();

				if (lastIndex != null && !lastIndex.isEmpty()) {
					if ("exit".equals(lastIndex)) {
						isExit = true;
					} else {
						QuikDataObject result = connector.getOHLCPrices(TestConnectorUser.datasourceDescriptor, Integer.parseInt(firstIndex), Integer.parseInt(lastIndex));
						if (result instanceof ErrorObject) {
							System.out.println(((ErrorObject) result).getErrorMessage());
							isExit = true;
						} else {
							System.out.println(result);
						}
					}
				} else {
					System.out.println("Invalid test type!");
				}
			}
		} else {
			System.out.println("Parameter datasourceDescriptor is unset. Create datasource first! Exiting...");
		}
    }

    private static void runGetDepoTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running get depo test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter security code or type exit:");
            String clientCode = "10021";
            String firmId = "NC0011100000";
            String message = reader.readLine();
            String account = "NL0011100043";
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getDepo(clientCode, firmId, message, account);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }


    private static void runGetMoneyTest(J2QuikConnector connector, BufferedReader reader) throws IOException {
        System.out.println("Running get money test...");
        System.out.println();

        boolean isExit = false;
        while (!isExit) {
            System.out.println();
            System.out.println("Enter currency code or type exit:");
            String clientCode = "10021";
            String firmId = "NC0011100000";
            String tag = "tag";
            String message = reader.readLine();
            if (message != null && !message.isEmpty()) {
                if ("exit".equals(message)) {
                    isExit = true;
                } else {
                    QuikDataObject result = connector.getMoney(clientCode, firmId, tag, message);
                    if (result instanceof ErrorObject) {
                        System.out.println(((ErrorObject) result).getErrorMessage());
                        isExit = true;
                    } else {
                        System.out.println(result);
                    }
                }
            } else {
                System.out.println("Invalid test type!");
            }
        }
    }

    private static String getRandomString() {
        return new BigInteger(130, random).toString();
    }
}