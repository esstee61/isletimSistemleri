import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class App extends JFrame {

    private JTextField txtDirectory, txtSearch;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private boolean filterState = true;  // filtre seçeneklerini açma kapama

    public App() {
        // pencere özellikleri
        setTitle("Dosya Arama Uygulaması");
        setMinimumSize(new Dimension(680, 460));
        setSize(1100, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Üst Panel (Dosya Yolu ve Aranacak Dosya Alanları)
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 16, 6));
        topPanel.setBorder(new EmptyBorder(12, 14, 8, 14));  // kenarlara boşluk bırakma

        // Gözat çubuğu
        JPanel dirPanel = new JPanel(new BorderLayout(16, 2));
        dirPanel.add(new JLabel(" Dosya Yolu:               "), BorderLayout.WEST);

        txtDirectory = new JTextField(System.getProperty("user.dir") + "\\deneme klasoru");
        dirPanel.add(txtDirectory, BorderLayout.CENTER);
        JButton btnBrowse = new JButton("     Dosya Seç   ");
        // buton stili
        btnBrowse.setBackground(new Color(219, 237, 243));
        btnBrowse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBrowse.setFocusable(false);
        dirPanel.add(btnBrowse, BorderLayout.EAST);

        // Arama çubuğu
        JPanel searchPanel = new JPanel(new BorderLayout(15, 2));
        searchPanel.add(new JLabel(" Aranacak Dosya:       "), BorderLayout.WEST);
        txtSearch = new JTextField();
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        JButton btnSearch = new JButton("           Bul          ");
        // buton stili
        btnSearch.setBackground(new Color(219, 237, 243));
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setFocusable(false);
        txtSearch.addActionListener(e -> btnSearch.doClick());  // enter basılınca butona bas
        searchPanel.add(btnSearch, BorderLayout.EAST);

        // Filtre Paneli
        Font filterFont = new Font("Dialog", Font.TRUETYPE_FONT, 11);
        JPanel filterPanel = new JPanel(new GridLayout(1, 11, 14, 4));
        JButton btnFilter = new JButton("filtre");
        // buton stili
        btnFilter.setFont(filterFont);
        btnFilter.setBackground(new Color(219, 237, 243));
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFilter.setFocusable(false);

        // filtreleme için inputlar
        JCheckBox checkMatch = new JCheckBox("Tam Eşleşme");
        JCheckBox checkHidden = new JCheckBox("Gizli Dosya");
        JCheckBox checkFiles = new JCheckBox("Sadece Dosyalar");
        JCheckBox checkFolders = new JCheckBox("Sadece Klasörler");
        JTextField txtMin = new JTextField("Min MB");
        JTextField txtMax = new JTextField("Mak MB");
        JTextField txtScore = new JTextField("Eşleşme oranı");
        // stil
        checkMatch.setFont(filterFont);
        checkHidden.setFont(filterFont);
        checkFiles.setFont(filterFont);
        checkFolders.setFont(filterFont);
        txtMin.setForeground(Color.LIGHT_GRAY);
        txtMax.setForeground(Color.LIGHT_GRAY);
        txtScore.setForeground(Color.LIGHT_GRAY);
        // ekleme
        filterPanel.add(txtScore);
        filterPanel.add(txtMin);
        filterPanel.add(txtMax);
        filterPanel.add(checkMatch);
        filterPanel.add(checkHidden);
        filterPanel.add(checkFolders);
        filterPanel.add(checkFiles);
        filterPanel.add(btnFilter, BorderLayout.EAST);
        // topPanele ekleme
        topPanel.add(dirPanel);
        topPanel.add(searchPanel);
        topPanel.add(filterPanel);

        // Orta Panel (Sonuçları Listeleyen Tablo)
        String[] columnNames = { "Dosya Adı", "Dosya Yolu", "Oluşturulma Tarihi", "Değiştirilme Tarihi", "Boyut" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Tüm hücreleri düzenlenemez yapar
            }
        };
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable); // scroll bar

        // Ana Ekrana Panel Eklemeleri
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // textfield'lar için girdi ipucusu (odakta değilken yazı gösterme)
        txtScore.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtScore.getText().equals("Eşleşme oranı")) {
                    txtScore.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtScore.getText().isEmpty()) {
                    txtScore.setText("Eşleşme oranı");
                }
            }
        });

        txtMin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMin.getText().equals("Min MB")) {
                    txtMin.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtMin.getText().isEmpty()) {
                    txtMin.setText("Min MB");
                }
            }
        });

        txtMax.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMax.getText().equals("Mak MB")) {
                    txtMax.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtMax.getText().isEmpty()) {
                    txtMax.setText("Mak MB");
                }
            }
        });

        // Sadece dosyalar ve sadece klasörler tiklerini senkronize eder
        checkFolders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  // checkbox'i tıklanamaz yapar
                checkFiles.setEnabled(!checkFolders.isSelected());
                checkFiles.setSelected(false);
            }
        });
        checkFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  // checkbox'i tıklanamaz yapar
                System.out.println(getSize());
                checkFolders.setEnabled(!checkFiles.isSelected());
                checkFolders.setSelected(false);
            }
        });

        // filtre butonu tıklanma fonksiyonu
        btnFilter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e))
                    return;  // sağ klikte fonksiyonu çalıştırmama için
                filterState = !filterState;  // boolean tersi
                if (filterState)
                    btnFilter.setBackground(new Color(219, 237, 243));
                else
                    btnFilter.setBackground(new Color(0, 129, 138));
                checkMatch.setVisible(filterState);
                checkFolders.setVisible(filterState);
                checkHidden.setVisible(filterState);
                checkFiles.setVisible(filterState);
                txtScore.setVisible(filterState);
                txtMin.setVisible(filterState);
                txtMax.setVisible(filterState);
            }
        });

        // Dosya seç Butonu: Dizini veya Dosyayı Seçme
        btnBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(
                        (txtDirectory.getText().isEmpty() ? "C:\\" : txtDirectory.getText()));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // Sadece dizin seçilsin
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDir = chooser.getSelectedFile();
                    txtDirectory.setText(selectedDir.getAbsolutePath());
                }
            }
        });

        // Tabloya MouseListener Eklenmesi
        resultTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Çift tıklama kontrolü
                    int selectedRow = resultTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String filePath = (String) tableModel.getValueAt(selectedRow, 1);  // Dosya yolu sütununu al
                        try {
                            Desktop.getDesktop().open(new File(filePath));  // Dosyayı veya dizini aç
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Dosya açılamıyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // Uygulama kapatıldığında threadpool'u sonlandırır
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ThreadPool.executor.shutdownNow();
                } finally {
                    System.out.println("threadpool sonlandırıldı.");
                }
            }
        });

        // Bul Butonu: input kontrol ve arama işlemi
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directoryPath = txtDirectory.getText();
                String searchText = txtSearch.getText();
                
                setTitle("Dosya Arama Uygulaması ~ taranıyor...");
                
                if (searchText.length() < 2) {
                    JOptionPane.showMessageDialog(null, "Lütfen en az iki harfli bir anahtar sözcük girin!",
                            "Uyarı", JOptionPane.WARNING_MESSAGE);
                    txtSearch.requestFocus();  // imleci arama çubuğuna atar
                    return;
                }

                if (!directoryPath.isEmpty()) {
                    File directory = new File(directoryPath);
                    if (directory.exists() && directory.isDirectory()) {
                        tableModel.setRowCount(0); // Önceki sonuçları temizle

                        if (filterState) {  // filtre ile arama

                            FileSearchFilter filter = new FileSearchFilter();
                            // integer inputs: string integer çevirme
                            try {
                                if (!txtScore.getText().isEmpty())
                                    filter.minSimilarity = Short.valueOf(txtScore.getText());
                                if (filter.minSimilarity > 100 || filter.minSimilarity < 0) {  
                                    JOptionPane.showMessageDialog(null, "0-100 arasında bir değer giriniz!", "Uyarı",
                                            JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                            } catch (NumberFormatException error) {
                                // error.printStackTrace();
                            }
                            try {
                                if (!txtMin.getText().isEmpty())
                                    filter.minSize = Long.valueOf(txtMin.getText()) * 1024;  // MB'den KB'a çevirme
                                if (filter.minSize < 0) {
                                    JOptionPane.showMessageDialog(null, "Eksi değer girdiniz!", "Uyarı",
                                            JOptionPane.WARNING_MESSAGE);
                                    filter.minSize = 0;
                                }
                            } catch (NumberFormatException error) {
                                // error.printStackTrace();
                            }
                            try {
                                if (!txtMax.getText().isEmpty())
                                    filter.maxSize = Long.valueOf(txtMax.getText()) * 1024;
                            } catch (NumberFormatException error) {
                                // error.printStackTrace();
                            }
                            // boolean inputs:
                            filter.exactMatch = checkMatch.isSelected();
                            filter.isHidden = checkHidden.isSelected();
                            filter.onlyFolders = checkFolders.isSelected();
                            filter.onlyFiles = checkFiles.isSelected();

                            System.out.println(filter.minSize);
                            searchFiles(new FileSearch(directoryPath, searchText, filter));

                        } else {  // filtresiz arama
                            searchFiles(new FileSearch(directoryPath, searchText));
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Geçerli bir dizin seçiniz!", "Hata",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir dosya yolu seçiniz!", "Uyarı",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // Arama sonrası dosyaları bilgilerini listeleme
    private void searchFiles(FileSearch fileSearch) {
        fileSearch.search();  // arama başlatma
        setTitle("Dosya Arama Uygulaması - " + fileSearch.results.size() + " tane dosya bulundu");

        Iterator<FileScore> it = fileSearch.results.iterator();
        while (it.hasNext()) {
            FileScore fileScore = it.next();
            try {
                BasicFileAttributes attrs = Files.readAttributes(fileScore.file.toPath(), BasicFileAttributes.class);

                // Tarih ve boyut formatlama
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String creationTime = dateFormat.format(attrs.creationTime().toMillis());
                String modifiedTime = dateFormat.format(attrs.lastModifiedTime().toMillis());
                String fileSize = String.format("%.2f", (float) fileScore.file.length() / (1024 * 1024)) + " MB";

                // Tabloya ekle
                tableModel.addRow(new Object[] {
                        fileScore.file.getName(),
                        fileScore.file.getAbsolutePath(),
                        creationTime,
                        modifiedTime,
                        fileSize.equals("0,00 MB") ? fileScore.file.isDirectory() ? " Klasör " : " Boş dosya "
                                                            : fileSize
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}