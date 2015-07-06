import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class WordCount extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res  = ToolRunner.run(new WordCount(), args);
        System.exit(res);
    }

    public int run(String[] args) throws  Exception {
        Job job  = Job.getInstance(getConf(), "wordcount"); // name of your job. It needs for logs.
        job.setJarByClass( this.getClass());
        FileInputFormat.addInputPaths(job, String.valueOf(new Path(args[0]))); // input folder
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // output folder. Must not exists before the start!
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
//        job.setNumReduceTasks(1);

        // for your homework you need to change lines below
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // till this line

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>{
        private static final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");

        public void map(LongWritable offset, Text lineText, Context context)
                throws IOException, InterruptedException {
            String line = lineText.toString();
            Text currentWord;
            for (String word : WORD_BOUNDARY.split(line)) {
                if (word.isEmpty()) {
                    continue;
                }
                currentWord = new Text(word);
                context.write(currentWord, new IntWritable(1));
            }
        }
    }

        public static class Reduce extends Reducer<Text,  IntWritable,  Text,  IntWritable> {
            @Override
            public void reduce(Text word, Iterable<IntWritable> counts, Context context)
                    throws IOException, InterruptedException {
                int sum = 0;
                for (IntWritable count : counts) {
                    sum += count.get();
                }
                context.write(word, new IntWritable(sum));
            }
        }
}
