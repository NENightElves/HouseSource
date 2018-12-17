import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class gui extends JFrame
{
    private JMenuBar menu;
    private JMenu menuSell, menuBuy, menuDeal, menuAuth, menuEdit, menuBin;
    private JMenuItem menuSellRead, menuSellAdd;
    private JMenuItem menuBuyRead, menuBuyAdd;
    private JMenuItem menuDealRead, menuDealAdd, menuDealAuto;
    private JMenuItem menuAuthRead, menuAuthAdd;
    private JMenuItem menuEditUpdate, menuEditDel, menuEditPic;
    private JMenuItem menuBinSellRead, menubinReverse;
    private MyTableModel tablemodel;
    private JTable table;
    private JTextField jf;
    private JButton jb;
    private JComboBox jc;
    private JPanel jp;
    private JScrollPane scroll;

    public gui(int x)
    {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuInit();
        jpInit();
//        this.add(menu, BorderLayout.NORTH);
        this.setJMenuBar(menu);
        this.add(jp, BorderLayout.SOUTH);
        this.setSize(500, 300);

        table = new JTable();
        scroll = new JScrollPane(table);
        table.getTableHeader().setReorderingAllowed(false);
        this.add(scroll, BorderLayout.CENTER);

        if (x == 1) table.setModel(getSellModel());
        else if (x == 2) table.setModel(getBuyModel());
        else if (x == 3) table.setModel(getDealModel());
        else if (x == 4) table.setModel(getAuthModel());
        this.setVisible(true);

    }

    private void menuInit()
    {
        menu = new JMenuBar();
        menuSell = new JMenu("出售");
        menuBuy = new JMenu("购买");
        menuDeal = new JMenu("成交");
        menuAuth = new JMenu("管理");
        menuEdit = new JMenu("编辑");
        menuBin = new JMenu("回收站");
        menuSellRead = new JMenuItem("读取");
        menuSellAdd = new JMenuItem("添加");
        menuBuyRead = new JMenuItem("读取");
        menuBuyAdd = new JMenuItem("添加");
        menuDealRead = new JMenuItem("读取");
        menuDealAdd = new JMenuItem("添加");
        menuDealAuto = new JMenuItem("自动匹配");
        menuAuthRead = new JMenuItem("读取权限");
        menuAuthAdd = new JMenuItem("添加权限");
        menuEditUpdate = new JMenuItem("修改");
        menuEditDel = new JMenuItem("删除");
        menuEditPic=new JMenuItem("查看图片");
        menuBinSellRead = new JMenuItem("读取");
        menubinReverse = new JMenuItem("还原");

        menuSellRead.addActionListener((e) -> table.setModel(getSellModel()));
        menuBuyRead.addActionListener((e) -> table.setModel(getBuyModel()));
        menuDealRead.addActionListener((e) -> table.setModel(getDealModel()));
        menuAuthRead.addActionListener((e) -> table.setModel(getAuthModel()));
        menuSellAdd.addActionListener((e) ->
        {
            new addSell();
            this.setVisible(false);
        });
        menuBuyAdd.addActionListener((e) ->
        {
            new addBuy();
            this.setVisible(false);
        });
        menuDealAdd.addActionListener((e) ->
        {
            new addDeal();
            this.setVisible(false);
        });
        menuAuthAdd.addActionListener((e) ->
        {
            new addAuth();
            this.setVisible(false);
        });
        menuEditUpdate.addActionListener((e) ->
        {
            if (staticvar.current == 1) setSell();
            else if (staticvar.current == 2) setBuy();
            else if (staticvar.current == 3) setDeal();
            else if (staticvar.current == 4) setAuth();
        });
        menuEditDel.addActionListener((e) ->
        {
            if (staticvar.current == 1) delTable("sell");
            else if (staticvar.current == 2) delTable("buy");
            else if (staticvar.current == 3) delTable("deal");
            else if (staticvar.current == 4) delTable("auth");
        });
        menuBinSellRead.addActionListener((e) -> table.setModel(getSellgModel()));
        menubinReverse.addActionListener((e) -> reverseSell());
        menuDealAuto.addActionListener((e) -> table.setModel(getAutoDealModel()));
        menuEditPic.addActionListener((e)->
        {
            if (table.getSelectedRows().length==1)
            {
                int[] x=table.getSelectedRows();
                new pic(table.getValueAt(x[0],6).toString());
            }
        });

        menu.add(menuSell);
        menu.add(menuBuy);
        menu.add(menuDeal);
        if (staticvar.authSta == true) menu.add(menuAuth);
        menu.add(menuEdit);
        menu.add(menuBin);
        menuSell.add(menuSellRead);
        menuSell.add(menuSellAdd);
        menuBuy.add(menuBuyRead);
        menuBuy.add(menuBuyAdd);
        menuDeal.add(menuDealRead);
        menuDeal.add(menuDealAdd);
        menuDeal.add(menuDealAuto);
        menuAuth.add(menuAuthRead);
        menuAuth.add(menuAuthAdd);
        menuEdit.add(menuEditUpdate);
        menuEdit.add(menuEditDel);
        menuEdit.add(menuEditPic);
        menuBin.add(menuBinSellRead);
        menuBin.add(menubinReverse);
    }

    private void jpInit()
    {
        jp = new JPanel();
        jf = new JTextField(30);
        jb = new JButton("查询");
        jc = new JComboBox();

        jb.addActionListener((e)->
        {
            String x="";
            String y="";
            String z="";
            if (staticvar.current==1) x="sell";
                else if (staticvar.current==2) x="buy";
//                    else if (staticvar.current==3) x="deal";
                        else return;
            if (jc.getSelectedItem().equals("编号")) y="no";
                else if (jc.getSelectedItem().equals("价格")) y="price";
            z=jf.getText();
            if (jc.getSelectedItem().equals("出售"))
            {
                y="rent";
                z="1";
            }
            else if (jc.getSelectedItem().equals("出租"))
            {
                y="rent";
                z="2";
            }
            table.setModel(getSearchModel(x,y,z));
        });

        jp.add(jf);
        jp.add(jb);
        jp.add(jc);
    }

    private MyTableModel getSellModel()
    {
        if (staticvar.authSta == true)
        {
            return getSellModel("select sell.* from sell");
        } else
        {
            return getSellModel("select sell.* from sell,auth where sell.no=auth.power and auth.name='" + staticvar.auth + "'");
        }
    }

    private MyTableModel getSellModel(String sql)
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
            rs = dbcon.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strSell> v = new ArrayList<strSell>();
            while (rs.next())
            {
                strSell person = new strSell();
                person.setNo(rs.getString("no"));
                person.setPeople(rs.getString("people"));
                person.setPhone(rs.getString("phone"));
                person.setAddress(rs.getString("address"));
                person.setSize(rs.getInt("size"));
                person.setRent(rs.getInt("rent"));
                person.setPic(rs.getString("pic"));
                person.setPrice(rs.getInt("price"));
                person.setStatus(rs.getString("status"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getNo(), v.get(i).getPeople(), v.get(i).getPhone(), v.get(i).getAddress(), v.get(i).getSize(),v.get(i).getRent(), v.get(i).getPic(), v.get(i).getPrice(), v.get(i).getStatus()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current = 1;
        tablemodel = tableModel;
        jc.removeAllItems();
        jc.addItem("编号");
        jc.addItem("价格");
        jc.addItem("出售");
        jc.addItem("出租");
        return tableModel;
    }

    private MyTableModel getBuyModel()
    {
        return  getBuyModel("select buy.* from buy");
    }
    private MyTableModel getBuyModel(String sql)
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
//            if (staticvar.authSta == true)
//            {
                rs = dbcon.executeQuery(sql);
//            } else
//            {
//                rs = dbcon.executeQuery("select buy.* from buy,auth where buy.no=auth.power and auth.name='" + staticvar.auth + "'");
//            }
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strBuy> v = new ArrayList<strBuy>();
            while (rs.next())
            {
                strBuy person = new strBuy();
                person.setNo(rs.getString("no"));
                person.setPeople(rs.getString("people"));
                person.setPhone(rs.getString("phone"));
                person.setAddress(rs.getString("address"));
                person.setSize(rs.getInt("size"));
                person.setRent(rs.getInt("rent"));
                person.setPrice(rs.getInt("price"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getNo(), v.get(i).getPeople(),v.get(i).getPhone(),v.get(i).getAddress(),v.get(i).getSize(), v.get(i).getRent(), v.get(i).getPrice()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current=2;
        tablemodel = tableModel;
        jc.removeAllItems();
        jc.addItem("编号");
        jc.addItem("价格");
        jc.addItem("出售");
        jc.addItem("出租");
        return tableModel;
    }

    private MyTableModel getDealModel()
    {
        return getDealModel("select * from deal");
    }
    private MyTableModel getDealModel(String sql)
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
            rs = dbcon.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strDeal> v = new ArrayList<strDeal>();
            while (rs.next())
            {
                strDeal person = new strDeal();
                person.setNo(rs.getString("no"));
                person.setSell(rs.getString("sell"));
                person.setBuy(rs.getString("buy"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getNo(), v.get(i).getSell(), v.get(i).getBuy()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current=3;
        tablemodel = tableModel;
        return tableModel;
    }

    private MyTableModel getAuthModel()
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
            rs = dbcon.executeQuery("select * from auth");
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strAuth> v = new ArrayList<strAuth>();
            while (rs.next())
            {
                strAuth person = new strAuth();
                person.setNo(rs.getString("no"));
                person.setName(rs.getString("name"));
                person.setPower(rs.getString("power"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getName(), v.get(i).getName(), v.get(i).getPower()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current=4;
        tablemodel = tableModel;
        return tableModel;
    }

    private MyTableModel getSellgModel()
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
            if (staticvar.authSta == true)
            {
                rs = dbcon.executeQuery("select sellg.* from sellg");
            } else
            {
                rs = dbcon.executeQuery("select sellg.* from sellg,auth where sellg.no=auth.power and auth.name='" + staticvar.auth + "'");
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strSell> v = new ArrayList<strSell>();
            while (rs.next())
            {
                strSell person = new strSell();
                person.setNo(rs.getString("no"));
                person.setPeople(rs.getString("people"));
                person.setPhone(rs.getString("phone"));
                person.setAddress(rs.getString("address"));
                person.setSize(rs.getInt("size"));
                person.setRent(rs.getInt("rent"));
                person.setPic(rs.getString("pic"));
                person.setPrice(rs.getInt("price"));
                person.setStatus(rs.getString("status"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getNo(), v.get(i).getPeople(),v.get(i).getPhone(), v.get(i).getAddress(),v.get(i).getSize(), v.get(i).getRent(), v.get(i).getPic(), v.get(i).getPrice(), v.get(i).getStatus()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current=5;
        tablemodel = tableModel;
        return tableModel;
    }

    private MyTableModel getAutoDealModel()
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        try
        {
            dbcon = new db();
            rs = dbcon.executeQuery("select sell.no sellno,buy.no buyno from sell,buy where sell.rent=buy.rent and sell.price<buy.price and sell.size>buy.size and sell.address like '%'+buy.address+'%'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int Colnum = rsmd.getColumnCount();
            int i;
            for (i = 1; i <= Colnum; i++)
                tableModel.addColumn(rsmd.getColumnName(i));
            ArrayList<strDealAuto> v = new ArrayList<strDealAuto>();
            while (rs.next())
            {
                strDealAuto person = new strDealAuto();
                person.setSellno(rs.getString("sellno"));
                person.setBuyno(rs.getString("buyno"));
                v.add(person);
            }
            rs.close();
            for (i = 0; i < v.size(); i++)
                tableModel.addRow(new Object[]{v.get(i).getSellno(), v.get(i).getBuyno()});
            dbcon.closeConn();
        } catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        staticvar.current=6;
        tablemodel = tableModel;
        return tableModel;
    }

    private MyTableModel getSearchModel(String table,String key,String value)
    {
        MyTableModel tableModel = new MyTableModel();
        db dbcon;
        ResultSet rs;
        String sql;
        sql="select * from "+table+" where "+key+"='"+value+"'";
        if (table.equals("sell")) return(getSellModel(sql));
            else if (table.equals("buy")) return (getSellModel(sql));
//                else if (table.equals("deal")) return (getDealModel(sql));
        return null;
    }

    private void setSell()
    {
        int i,index,count;
        db dbcon=new db();
        if (table.getCellEditor()!=null)
        {
            table.getCellEditor().stopCellEditing();
        }
        try
        {
            String sql="update sell set people=?,phone=?,address=?,size=?,rent=?,pic=?,price=?,status=? where no=?";
            PreparedStatement presta=dbcon.PreparedStatement(sql);
            count=tablemodel.getEditedIndex().size();
            if (count>0)
            {
                for(i=0;i<count;i++)
                {
                    index=tablemodel.getEditedIndex().get(i);
                    presta.setString(1,table.getValueAt(index,1).toString());
                    presta.setString(2,table.getValueAt(index,2).toString());
                    presta.setString(3,table.getValueAt(index,3).toString());
                    presta.setString(4,table.getValueAt(index,4).toString());
                    presta.setString(5,table.getValueAt(index,5).toString());
                    presta.setString(6,table.getValueAt(index,6).toString());
                    presta.setString(7,table.getValueAt(index,7).toString());
                    presta.setString(8,table.getValueAt(index,8).toString());
                    presta.setString(9,table.getValueAt(index,0).toString());
                    presta.addBatch();
                }
            }
            presta.executeBatch();
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }

    private void setBuy()
    {
        int i,index,count;
        db dbcon=new db();
        if (table.getCellEditor()!=null)
        {
            table.getCellEditor().stopCellEditing();
        }
        try
        {
            String sql="update buy set people=?,phone=?,address=?,size=?,rent=?,price=? where no=?";
            PreparedStatement presta=dbcon.PreparedStatement(sql);
            count=tablemodel.getEditedIndex().size();
            if (count>0)
            {
                for(i=0;i<count;i++)
                {
                    index=tablemodel.getEditedIndex().get(i);
                    presta.setString(1,table.getValueAt(index,1).toString());
                    presta.setString(2,table.getValueAt(index,2).toString());
                    presta.setString(3,table.getValueAt(index,3).toString());
                    presta.setString(4,table.getValueAt(index,4).toString());
                    presta.setString(5,table.getValueAt(index,5).toString());
                    presta.setString(6,table.getValueAt(index,6).toString());
                    presta.setString(7,table.getValueAt(index,0).toString());
                    presta.addBatch();
                }
            }
            presta.executeBatch();
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }

    private void setDeal()
    {
        int i,index,count;
        db dbcon=new db();
        if (table.getCellEditor()!=null)
        {
            table.getCellEditor().stopCellEditing();
        }
        try
        {
            String sql="update deal set sell=?,buy=? where no=?";
            PreparedStatement presta=dbcon.PreparedStatement(sql);
            count=tablemodel.getEditedIndex().size();
            if (count>0)
            {
                for(i=0;i<count;i++)
                {
                    index=tablemodel.getEditedIndex().get(i);
                    presta.setString(1,table.getValueAt(index,1).toString());
                    presta.setString(2,table.getValueAt(index,2).toString());
                    presta.setString(3,table.getValueAt(index,0).toString());
                    presta.addBatch();
                }
            }
            presta.executeBatch();
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }

    private void setAuth()
    {
        int i,index,count;
        db dbcon=new db();
        if (table.getCellEditor()!=null)
        {
            table.getCellEditor().stopCellEditing();
        }
        try
        {
            String sql="update auth set name=?,power=? where no=?";
            PreparedStatement presta=dbcon.PreparedStatement(sql);
            count=tablemodel.getEditedIndex().size();
            if (count>0)
            {
                for(i=0;i<count;i++)
                {
                    index=tablemodel.getEditedIndex().get(i);
                    presta.setString(1,table.getValueAt(index,1).toString());
                    presta.setString(2,table.getValueAt(index,2).toString());
                    presta.setString(3,table.getValueAt(index,0).toString());
                    presta.addBatch();
                }
            }
            presta.executeBatch();
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }

    private void delTable(String str)
    {
        db dbcon=new db();
        try
        {
            if (table.getSelectedRows().length>0)
            {
                int[] selRowIndexs=table.getSelectedRows();
                String sql;
                if (str.equals("sell")) sql="insert into "+str+"g select * from "+str+" where no=? "+"delete from "+str+" where no=?";
                    else sql="delete from "+str+" where no=?";
                PreparedStatement presta=dbcon.PreparedStatement(sql);
                for(int i=0;i<selRowIndexs.length;i++)
                {
                    if (str.equals("sell"))
                    {
                        presta.setString(1, table.getValueAt(selRowIndexs[i], 0).toString());
                        presta.setString(2, table.getValueAt(selRowIndexs[i], 0).toString());
                    }
                    else
                    {
                        presta.setString(1, table.getValueAt(selRowIndexs[i], 0).toString());
                    }
                    presta.addBatch();
                }
                presta.executeBatch();
                if (str.equals("sell")) table.setModel(getSellModel());
                    else if (str.equals("buy")) table.setModel(getBuyModel());
                        else if (str.equals("deal")) table.setModel(getDealModel());
                            else if (str.equals("auth")) table.setModel(getAuthModel());
            }
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }

    private void reverseSell()
    {
        if (staticvar.current!=5) {JOptionPane.showMessageDialog(null,"非法还原");return;}
        db dbcon=new db();
        try
        {
            if (table.getSelectedRows().length>0)
            {
                int[] selRowIndexs=table.getSelectedRows();
                String sql;
                sql="insert into sell select * from sellg where no=? delete from sellg where no=?";
                PreparedStatement presta=dbcon.PreparedStatement(sql);
                for(int i=0;i<selRowIndexs.length;i++)
                {
                    presta.setString(1, table.getValueAt(selRowIndexs[i], 0).toString());
                    presta.setString(2, table.getValueAt(selRowIndexs[i], 0).toString());
                    presta.addBatch();
                }
                presta.executeBatch();
                table.setModel(getSellgModel());
            }
        }
        catch(SQLException sqle)
        {
            System.out.println(sqle.toString());
        }
    }


    public static void main(String[] args)
    {
        gui x=new gui(1);
    }
}
